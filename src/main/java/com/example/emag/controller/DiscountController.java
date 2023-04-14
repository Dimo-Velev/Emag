package com.example.emag.controller;

import com.example.emag.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;

public class DiscountController extends AbstractController{

    @Autowired
    private DiscountService discountService;

}
