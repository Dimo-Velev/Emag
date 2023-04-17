package com.example.emag.service;

import com.example.emag.model.DTOs.FavoriteProductDTO;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.repositories.CartContentRepository;
import com.example.emag.model.repositories.ProductRepository;
import com.example.emag.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected CartContentRepository cartContentRepository;


    protected User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
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
