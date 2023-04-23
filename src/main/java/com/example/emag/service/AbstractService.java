package com.example.emag.service;

import com.example.emag.model.util.EmailSender;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.User;
import com.example.emag.model.entities.*;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected CartContentRepository cartContentRepository;
    @Autowired
    protected ReviewRepository reviewRepository;
    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected EmailSender emailSender;
    @Autowired
    protected  DiscountRepository discountRepository;

    protected Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
    }

    protected User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected Discount getDiscountById(int id) {
        return discountRepository.findById(id).orElseThrow(() -> new NotFoundException("Discount not found"));
    }

    protected Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found."));
    }

    protected Review getReviewById(int id) {
        return reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found."));
    }

    protected double calculatePrice(Set<CartContent> products) {
        return products.stream()
                .mapToDouble(cartContent -> {
                    double price = cartContent.getProduct().getPrice();
                    if (cartContent.getProduct().getDiscount() != null) {
                        double discount = cartContent.getProduct().getDiscount().getDiscountPercent() / 100.0;
                        price -= price * discount;
                    }
                    return price * cartContent.getQuantity();
                })
                .sum();
    }

    protected void sendEmail(Product product){
        Set<User> subscribers = product.getUserFavourites();
        subscribers.addAll(product.getProductInCarts().stream()
                .map(cartContent -> cartContent.getUser())
                .collect(Collectors.toSet()));

        subscribers.stream()
                .filter(user -> user.isSubscribed())
                .forEach(user -> emailSender.sendMessage(user.getEmail(),product));
    }
}

