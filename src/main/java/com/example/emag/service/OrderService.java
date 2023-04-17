package com.example.emag.service;

import com.example.emag.model.DTOs.order.CreatedOrderDTO;
import com.example.emag.model.DTOs.order.OrderWithFewInfoDTO;
import com.example.emag.model.entities.CartContent;
import com.example.emag.model.entities.Order;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.OrderRepository;
import com.example.emag.model.repositories.OrderStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public CreatedOrderDTO createOrder(int id) {
        Set<CartContent> products = getProductsFromCart(id);
        Order order = createNewOrder(products, getUserById(id));


        return new CreatedOrderDTO();
    }

    private Set<CartContent> getProductsFromCart(int id) {
        checkIfCartIsEmpty(id);
        User user = getUserById(id);
        Set<CartContent> productsInCart = user.getProductsInCart();
        productsInCart
                .forEach(cartContent -> cartContentRepository.deleteById(cartContent.getId()));
        return productsInCart;
    }

    private Order createNewOrder(Set<CartContent> products, User user) {
        Order order = new Order();
        order.setPrice(calculatePrice(products));
        order.setUser(user);
        //order.set
        //TODO LYUBO REQUESTED ME TO PUSH :D :D
        return new Order();
    }

    private void checkIfCartIsEmpty(int id) {
       CartContent cartContent = cartContentRepository.findByUserId(id);
       if (cartContent == null){
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
