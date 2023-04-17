package com.example.emag.service;

import com.example.emag.model.entities.*;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.repositories.*;
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
    protected PaymentTypeRepository paymentTypeRepository;
    @Autowired
    protected AddressRepository addressRepository;
    @Autowired
    protected OrderContentRepository orderContentRepository;
    protected Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Parent category not found"));
    }
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
