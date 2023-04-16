package com.example.emag.service;

import com.example.emag.model.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService extends AbstractService{

    @Autowired
    private DiscountRepository discountRepository;
}
