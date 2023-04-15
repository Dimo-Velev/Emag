package com.example.emag.controller;

import com.example.emag.model.DTOs.*;
import com.example.emag.model.entities.Product;
import com.example.emag.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController extends AbstractController{

    @Autowired
    private UserService userService;

    //register
    @PostMapping("/users/signup")
    public UserWithoutPassDTO register(@Valid @RequestBody RegisterDTO dto){
        return userService.register(dto);
    }

    //login
    @PostMapping("/login")
    public UserWithoutPassDTO login(@RequestBody LoginDTO dto, HttpSession s){
        UserWithoutPassDTO respDto = userService.login(dto);
        s.setAttribute("LOGGED", true);
        s.setAttribute("LOGGED_ID", respDto.getId());
        return respDto;
    }

    @PostMapping("/logout")
    public void logout(HttpSession s){
        s.invalidate();
    }

    @PostMapping("/password")
    public UserWithoutPassDTO changePass(){
        //TODO
        return null;
    }

    //view my account
    @GetMapping("/my-account")
    public UserWithoutPassDTO viewUserInfo(HttpSession s){
        int userId = getLoggedId(s);
        UserWithoutPassDTO respDto = userService.viewUserInfo(userId);
        return respDto;
    }

    @GetMapping("/history")
    public List<Product> viewUserHistory(){
        //TODO view product history
        return null;
    }
    @PutMapping("/my-account")
    public void editUserInfo(@RequestBody EditProfileDTO dto,HttpSession s){
        userService.editUserInfo(dto, getLoggedId(s));
    }

    @PutMapping("/subscription")
    public void editSubscription(HttpSession s){
        userService.editSubscription(getLoggedId(s));
    }
}
