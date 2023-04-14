package com.example.emag.controller;

import com.example.emag.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderController extends AbstractController{

    @Autowired
    private OrderService orderService;
}
