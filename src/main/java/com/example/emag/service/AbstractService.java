package com.example.emag.service;

import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ModelMapper mapper;


    protected User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected boolean ifUserExists(int id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        return true;
    }
}
