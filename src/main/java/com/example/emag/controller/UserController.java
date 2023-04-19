package com.example.emag.controller;

import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.model.DTOs.user.*;
import com.example.emag.model.entities.Product;
import com.example.emag.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController extends AbstractController{

    @Autowired
    private UserService userService;

    @PostMapping("/users/signup")
    public UserWithoutPassDTO register(@Valid @RequestBody RegisterDTO dto){
        return userService.register(dto);
    }

    @PostMapping("/login")
    public UserWithoutPassDTO login(@Valid @RequestBody LoginDTO dto, HttpSession s){
        UserWithoutPassDTO respDto = userService.login(dto);
        s.setAttribute("LOGGED_ID", respDto.getId());
        return respDto;
    }

    @PostMapping("/logout")
    public void logout(HttpSession s){
        s.invalidate();
    }

    @PostMapping("/password")
    public UserWithoutPassDTO changePass(@Valid @RequestBody ChangePassDTO dto, HttpSession s ){
        int userId = getLoggedId(s);
        return userService.changePass(dto,userId);
    }

    @GetMapping("/my-account")
    public UserWithoutPassDTO viewUserInfo(HttpSession s){
        int userId = getLoggedId(s);
        return userService.viewUserInfo(userId);
    }

    @GetMapping("/history")
    public List<ProductViewDTO> viewUserHistory(HttpSession s){
        int userId = getLoggedId(s);
        return userService.viewUserHistory(userId);
    }
//    @Role()
//    @Secured({"admin"})

    @PutMapping("/my-account")
    public void editUserInfo(@Valid @RequestBody EditProfileDTO dto, HttpSession s){
        userService.editUserInfo(dto, getLoggedId(s));
    }

    @PutMapping("/subscription")
    public void editSubscription(HttpSession s){
        userService.editSubscription(getLoggedId(s));
    }
}
