package com.example.emag.service;

import com.example.emag.model.DTOs.address.OrderAddressDTO;
import com.example.emag.model.DTOs.order.CreatedOrderDTO;
import com.example.emag.model.DTOs.order.OrderWithFewInfoDTO;
import com.example.emag.model.DTOs.order.CreateOrderDTO;
import com.example.emag.model.DTOs.orderContent.OrderContentDTO;
import com.example.emag.model.DTOs.product.ProductViewDTO;
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
    public CreatedOrderDTO createOrder(CreateOrderDTO dto, int userId) {
        User user = getUserById(userId);
        PaymentType paymentType = paymentTypeRepository.findById(dto.getPaymentId()).
                orElseThrow(() -> new BadRequestException("That payment type doesn't exist."));
        Address address = user.getAddresses().stream().filter(address1 -> address1.getId() == dto.getAddressId()).findAny().orElseThrow(() ->
                new BadRequestException("That address doesn't exist in your addresses."));
        Set<CartContent> products = getProductsFromCart(user);
        Order order = createNewOrder(products, user, paymentType, address);
        CreatedOrderDTO dtoOrder = new CreatedOrderDTO();
        dtoOrder.setName("Bill" + order.getId());
        dtoOrder.setCreatedAt(order.getCreatedAt());
        dtoOrder.setAddress(mapper.map(order.getAddress(), OrderAddressDTO.class));
        dtoOrder.setProducts(order.getProductsInOrder().stream()
                .map(orderContent -> {
                    OrderContentDTO orderDto = new OrderContentDTO();
                    orderDto.setName(orderContent.getProduct().getName());
                    orderDto.setQuantity(orderContent.getQuantity());
                    return orderDto;
                })
                .collect(Collectors.toList()));
        dtoOrder.setTotalPrice(order.getPrice());
        return dtoOrder;
    }

    private Set<CartContent> getProductsFromCart(User user) {
        cartContentRepository.findByUserId(user.getId()).orElseThrow(() -> new BadRequestException("The cart is empty."));
        Set<CartContent> productsInCart = user.getProductsInCart();
        checkEachProductQuantityInDB(productsInCart);
        productsInCart.forEach(cartContent -> cartContentRepository.deleteById(cartContent.getId()));
        return productsInCart;
    }

    private void checkEachProductQuantityInDB(Set<CartContent> productsInCart) {
        productsInCart.forEach(cartContent -> {
            Product product = productRepository.findById(cartContent.getProduct().getId()).orElseThrow(
                    () -> new BadRequestException("Product not found." + cartContent.getProduct().getName()));
            if (product.getQuantity() < cartContent.getQuantity()) {
                throw new BadRequestException("Not enough quantity of " + product.getName());
            }
        });
    }

    private Order createNewOrder(Set<CartContent> products, User user, PaymentType paymentType, Address address) {
        Order order = new Order();
        order.setPrice(calculatePrice(products)); //calculating the price in private method, checking if there is discount per product and summing total price
        order.setUser(user); // adding user to the order
        order.setPaymentType(paymentType); // setting payment type, which we have in the DB from the start
        order.setAddress(address); // setting the address for delivery
        Set<OrderContent> orderContents = new HashSet<>(); //creating new Set of Order contents that we are going fill up later
        Set<Product> editedQuantityProducts = new HashSet<>();
        //Setting status to the order, which we have from the beginning in the DB
        order.setStatus(orderStatusRepository.findById(1).orElseThrow(() -> new BadRequestException("This should never happen.")));
        orderRepository.save(order);
        //Foreach to map cart content to order content
        products.forEach(cartContent -> {
            OrderContentKey key = new OrderContentKey();
            OrderContent orderContent = new OrderContent();
            orderContent.setOrder(order);
            orderContent.setProduct(cartContent.getProduct());
            orderContent.setQuantity(cartContent.getQuantity());
            key.setProductId(cartContent.getProduct().getId());
            key.setOrderId(order.getId());
            orderContent.setId(key);
            //Finding the product IDs to check if every product has enough quantity
            Product product = productRepository.findById(orderContent.getProduct().getId()).orElseThrow(
                    () -> new BadRequestException("Product not found." + orderContent.getProduct().getName()));
            product.setQuantity(product.getQuantity() - orderContent.getQuantity());
            editedQuantityProducts.add(product); // adding products in set to save them all later in DB
            orderContents.add(orderContent); //adding the contents of the order to a Set of Order contents that we are going to put to the order
        });
        order.setProductsInOrder(orderContents); // setting the contents to the order
        productRepository.saveAll(editedQuantityProducts);
        orderContentRepository.saveAll(orderContents);
        return order;
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
