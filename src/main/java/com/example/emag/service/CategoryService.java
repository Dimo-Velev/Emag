package com.example.emag.service;

import com.example.emag.model.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService {

    @Autowired
    private CategoryRepository categoryRepository;
}
