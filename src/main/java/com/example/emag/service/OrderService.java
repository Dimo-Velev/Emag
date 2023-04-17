package com.example.emag.service;

import com.example.emag.model.DTOs.order.CreatedOrderDTO;
import com.example.emag.model.DTOs.order.OrderWithFewInfoDTO;
import com.example.emag.model.entities.*;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.OrderRepository;
import com.example.emag.model.repositories.OrderStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService extends AbstractService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    public List<OrderWithFewInfoDTO> getAllOrders(int id) {
        List<Order> orderList = orderRepository.getAllByUserId(id);
        return orderList.stream()
                .map(order -> mapper.map(order, OrderWithFewInfoDTO.class))
                .collect(Collectors.toList());
    }

    public OrderWithFewInfoDTO getOrderById(int id, int userId) {
        Order order = findOrderById(id);
        checkIfItsUserOrder(order, userId);
        return mapper.map(order, OrderWithFewInfoDTO.class);
    }

    public OrderWithFewInfoDTO cancelOrderById(int id, int userId) {
        Order order = findOrderById(id);
        checkIfItsUserOrder(order, userId);
        order.setStatus(orderStatusRepository.findById(7).orElseThrow(() -> new NotFoundException("This should never happen.")));
        orderRepository.save(order);
        return mapper.map(order, OrderWithFewInfoDTO.class);
    }

    private Order findOrderById(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Card not found."));
    }

    private void checkIfItsUserOrder(Order order, int userId) {
        if (order.getUser().getId() != userId) {
            throw new UnauthorizedException("You have no access to this resource.");
        }
    }

    @Transactional
    public CreatedOrderDTO createOrder(int paymentId, int userId) {
        PaymentType paymentType = paymentTypeRepository.findById(paymentId).orElseThrow(() -> new BadRequestException("That payment type doesn't exist."));
        Set<CartContent> products = getProductsFromCart(userId);
        Order order = createNewOrder(products, getUserById(userId), paymentType);
        return mapper.map(order, CreatedOrderDTO.class);
    }

    private Set<CartContent> getProductsFromCart(int id) {
        checkIfCartIsEmpty(id);
        User user = getUserById(id);
        Set<CartContent> productsInCart = user.getProductsInCart();
        productsInCart.forEach(cartContent -> cartContentRepository.deleteById(cartContent.getId()));
        return productsInCart;
    }

    private Order createNewOrder(Set<CartContent> products, User user, PaymentType paymentType) {
        Order order = new Order();
        order.setPrice(calculatePrice(products)); //calculating the price in private method, checking if there is discount per product and summing total price
        order.setUser(user); // adding user to the order
        order.setPaymentType(paymentType); // setting payment type, which we have in the DB from the start
        Set<OrderContent> orderContents = new HashSet<>(); //creating new Set of Order contents that we are going fill up later
        //Setting status to the order, which we have from the beginning in the DB
        order.setStatus(orderStatusRepository.findById(1).orElseThrow(() -> new BadRequestException("This should never happen.")));
        //Foreach to map cart content to order content
        products.forEach(cartContent -> {
            OrderContent orderContent = mapper.map(cartContent, OrderContent.class);
            //Saving each orderContent in DB
            orderContentRepository.save(orderContent);
            //Finding the product IDs to check if every product has enough quantity
            Product product = productRepository.findById(orderContent.getProduct().getId()).orElseThrow(
                    () -> new BadRequestException("Product not found." + orderContent.getProduct().getName()));
            if (product.getQuantity() < orderContent.getQuantity()) { //checking if enough quantity of product IN DB
                throw new BadRequestException("Not enough quantity of " + product.getName());
            }
            product.setQuantity(product.getQuantity() - orderContent.getQuantity());
            productRepository.save(product); //updating product with the new quantity after selling the product in DB
            orderContents.add(orderContent); //adding the contents of the order to a Set of Order contents that we gonna put to the order
        });
        order.setProductsInOrder(orderContents); // setting the contents to the order
        orderRepository.save(order); // saving order in DB
        return order;
    }

    private void checkIfCartIsEmpty(int id) {
        CartContent cartContent = cartContentRepository.findByUserId(id);
        if (cartContent == null) {
            throw new BadRequestException("Cart is empty");
        }
    }

    private double calculatePrice(Set<CartContent> products) {
        return products.stream()
                .mapToDouble(cartContent -> {
                    double price = cartContent.getProduct().getPrice();
                    if (cartContent.getProduct().getDiscount() != null) {
                        double discount = cartContent.getProduct().getDiscount().getDiscountPercent() / 100.0;
                        price -= price * discount;
                    }
                    return price * cartContent.getQuantity();
                })
                .sum();
    }
}
