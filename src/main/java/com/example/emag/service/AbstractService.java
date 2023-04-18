package com.example.emag.service;

import com.example.emag.model.entities.Discount;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.repositories.*;
import com.example.emag.model.entities.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected CartContentRepository cartContentRepository;
    @Autowired
    protected DiscountRepository discountRepository;

    protected Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Parent category not found"));
    }
    protected User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
    protected Discount getDiscountbyId(int id){
        return discountRepository.findById(id).orElseThrow(() -> new NotFoundException("Discount not found"));

    }

    protected boolean ifUserExists(int id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        return true;
    }

    protected Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found."));
    }

    protected double getProductPrice(int id) {
        Product product = getProductById(id);
        if (product.getDiscount() != null) {
            return product.getPrice() - (product.getPrice() * product.getDiscount().getDiscountPercent());
        } else {
            return product.getPrice();
        }
    }
}
