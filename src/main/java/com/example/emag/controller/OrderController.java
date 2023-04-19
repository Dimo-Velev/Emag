package com.example.emag.controller;

import com.example.emag.model.DTOs.order.CreatedOrderDTO;
import com.example.emag.model.DTOs.order.OrderWithFewInfoDTO;
import com.example.emag.model.DTOs.order.CreateOrderDTO;
import com.example.emag.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController extends AbstractController{

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<OrderWithFewInfoDTO> getAll(HttpSession session){
        return orderService.getAllOrders(getLoggedId(session));
    }
    @GetMapping("/orders/{id:\\d+}")
    public OrderWithFewInfoDTO getById(@Valid @PathVariable int id, HttpSession session){
        return orderService.getOrderById(id,getLoggedId(session));
    }
    @PutMapping("/orders/{id:\\d+}")
    public OrderWithFewInfoDTO cancel(@Valid @PathVariable int id,HttpSession session){
        return orderService.cancelOrderById(id,getLoggedId(session));
    }
    @PostMapping("/orders")
    public CreatedOrderDTO create(@Valid @RequestBody CreateOrderDTO dto, HttpSession session){
        return orderService.createOrder(dto,getLoggedId(session));
    }
}
