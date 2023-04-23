package com.example.emag.controller;

import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.model.DTOs.user.*;
import com.example.emag.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
        s.setAttribute(LOGGED_ID, respDto.getId());
        return respDto;
    }

    @PostMapping("/logout")
    public void logout(HttpSession s){
        s.invalidate();
    }

    @PostMapping("/password")
    public UserWithoutPassDTO changePass(@Valid @RequestBody ChangePassDTO dto, HttpSession s ){
        return userService.changePass(dto,getLoggedId(s));
    }

    @GetMapping("/my-account")
    public UserWithoutPassDTO viewUserInfo(HttpSession s){
        return userService.viewUserInfo(getLoggedId(s));
    }

    @GetMapping("/history")
    public Page<ProductViewDTO> viewUserHistory(HttpSession s, Pageable pageable){
        return userService.viewUserHistory(getLoggedId(s), pageable);
    }

    @PutMapping("/my-account")
    public void editUserInfo(@Valid @RequestBody EditProfileDTO dto, HttpSession s){
        userService.editUserInfo(dto, getLoggedId(s));
    }

    @PutMapping("/subscription")
    public void editSubscription(HttpSession s){
        userService.editSubscription(getLoggedId(s));
    }

    @PutMapping("/users/{id:\\d+}/admin/")
    public ResponseEntity<String> setUserAsAdmin(HttpSession s, @PathVariable int id){
        isLoggedAdmin(s);
        userService.setUserAsAdmin(id);
        return ResponseEntity.ok("User" + id + " was promoted to admin");
    }





}
