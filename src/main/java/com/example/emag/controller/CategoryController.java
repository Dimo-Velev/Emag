package com.example.emag.controller;

import com.example.emag.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryController extends AbstractController{

    @Autowired
    private CategoryService categoryService;
}
