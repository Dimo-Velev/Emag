package com.example.emag.controller;

import com.example.emag.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductController extends AbstractController{

    @Autowired
    private ProductService productService;


}
