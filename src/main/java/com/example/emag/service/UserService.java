package com.example.emag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

public class UserService extends AbstractService{

    @Autowired
    private BCryptPasswordEncoder encoder;




}