package com.example.emag.controller;

import com.example.emag.model.DTOs.category.CategoryAddDTO;
import com.example.emag.model.DTOs.category.CategoryViewAddedDTO;
import com.example.emag.model.DTOs.category.CategoryViewDTO;
import com.example.emag.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController extends AbstractController{

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public CategoryViewAddedDTO addCategory(@Valid @RequestBody CategoryAddDTO c, HttpSession s){
        isLoggedAdmin(s);
        return categoryService.addCategory(c);
    }

    @GetMapping("/categories/{id:\\d+}")
    public CategoryViewDTO viewCategoryById(@PathVariable int id){
        return categoryService.viewCategoryById(id);
    }

    @DeleteMapping("/categories/{id:\\d+}")
    public CategoryViewDTO deleteCategoryById(@PathVariable int id, HttpSession s){
        isLoggedAdmin(s);
        return categoryService.deleteCategoryById(id);
    }




}
