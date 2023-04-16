package com.example.emag.service;

import com.example.emag.model.DTOs.CreatedOrderDTO;
import com.example.emag.model.DTOs.OrderWithFewInfoDTO;
import com.example.emag.model.entities.Order;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.OrderRepository;
import com.example.emag.model.repositories.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        checkIfItsUserOrder(order,userId);
        order.setStatus(orderStatusRepository.findById(7).orElseThrow(() -> new NotFoundException("This should never happen.")));
        orderRepository.save(order);
        return mapper.map(order,OrderWithFewInfoDTO.class);
    }

    private Order findOrderById(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Card not found."));
    }

    private void checkIfItsUserOrder(Order order, int userId) {
        if (order.getUser().getId() != userId) {
            throw new UnauthorizedException("You have no access to this resource.");
        }
    }

    public CreatedOrderDTO createOrder(int id) {
        //TODO
       return new CreatedOrderDTO();
    }
}
