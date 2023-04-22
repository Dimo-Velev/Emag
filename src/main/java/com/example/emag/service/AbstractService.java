package com.example.emag.service;

import com.example.emag.model.EmailSender;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.User;
import com.example.emag.model.entities.*;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.repositories.*;
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
    protected CategoryRepository categoryRepository;
    @Autowired
    protected CartContentRepository cartContentRepository;
    @Autowired
    protected ReviewRepository reviewRepository;
    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected EmailSender emailSender;


    protected Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
    }
    protected User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
    protected Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found."));
    }
    protected Review getReviewById(int id){
       return reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found."));
    }
    protected void sendEmail(Product product){
        List<User> subscribers = product.getUserFavourites();
        for (User user : subscribers){
            if(user.isSubscribed()) {
                emailSender.sendMessage(user.getEmail(), product);
            }
        }
    }


}
