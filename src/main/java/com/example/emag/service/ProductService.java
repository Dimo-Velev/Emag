package com.example.emag.service;

import com.example.emag.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractService {

    @Autowired
    private ProductRepository productRepository;
}
