package com.example.emag.controller;


import com.example.emag.model.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class AbstractController {

    protected int getLoggedId(HttpSession s){
        if(s.getAttribute("LOGGED_ID") == null){
            throw new UnauthorizedException("You have to login first");
        }
        return (int) s.getAttribute("LOGGED_ID");
    }
}
