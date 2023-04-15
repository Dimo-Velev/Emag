package com.example.emag.controller;

import com.example.emag.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController extends AbstractController{

    @Autowired
    private ProductService productService;


}
