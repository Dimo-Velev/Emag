package com.example.emag.controller;

import com.example.emag.model.DTOs.ErrorDTO;
import com.example.emag.model.exceptions.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
public abstract class AbstractController {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(Exception e) {
        return generateErrorDTO(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorized(Exception e) {
        return generateErrorDTO(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception e) {
        return generateErrorDTO(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleNotReadable(Exception e) {
        return generateErrorDTO(e, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleRest(Exception e) {
        return generateErrorDTO(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ErrorDTO generateErrorDTO(Exception e, HttpStatus s) {
        return ErrorDTO.builder()
                .msg(e.getMessage())
                .time(LocalDateTime.now())
                .status(s.value())
                .build();
    }


//    protected int getLoggedId(HttpSession s) {
//        isLogged(s);
//        return (int) s.getAttribute("LOGGED_ID");
//    }
//    protected boolean isLogged(HttpSession s) {
//        if (s.getAttribute("LOGGED_ID") == null) {
//            throw new UnauthorizedException("You have to lo gin first");
//        }
//        return true;
//    }

    protected int getLoggedId(HttpSession s){
        if(!isLogged(s)){
            throw new UnauthorizedException("You have to login first");
        }
        return (int) s.getAttribute("LOGGED_ID");
    }
    protected boolean isLogged(HttpSession s) {
        return s.getAttribute("LOGGED_ID") != null;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return generateErrorDTO(errors, HttpStatus.BAD_REQUEST);
    }

    private ErrorDTO generateErrorDTO(Object msg, HttpStatus status) {
        return ErrorDTO.builder()
                .msg(msg)
                .time(LocalDateTime.now())
                .status(status.value())
                .build();
    }

    protected boolean isLoggedAdmin(HttpSession session) {
        //TODO
        isLogged(session);
        return false;
    }
}
