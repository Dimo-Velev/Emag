package com.example.emag.controller;

import com.example.emag.model.DTOs.review.*;
import com.example.emag.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController extends AbstractController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("products/{id:\\d+}/reviews")
    public ReviewWithFewInfoDTO createForProduct(@Valid @RequestBody CreateReviewDTO dto, @Valid @PathVariable int id, HttpSession session){
        return reviewService.createReviewForProduct(dto,id,getLoggedId(session));
    }
    @GetMapping("/products/{id:\\d+}/reviews")
    public List<ReviewWithoutPicDTO> viewAllForProduct(@PathVariable int id){
        return reviewService.viewAllReviewsForProduct(id);
    }
    @GetMapping("/reviews")
    public List<ReviewWithoutPicAllDTO> viewAllOwn(HttpSession session) {
        return reviewService.viewAllOwnReviews(getLoggedId(session));
    }
    @PutMapping("/reviews/{id:\\d+}")
    public ReviewWithoutPicAllDTO edit(@Valid @RequestBody EditReviewDTO dto,@Valid@PathVariable int id, HttpSession session){
        return reviewService.editReviewOnProduct(dto,id,getLoggedId(session));
    }
    @PutMapping("/reviews/{id:\\d+}/react")
    public LikedReviewDTO likeDislikeReview(@Valid @PathVariable int id, HttpSession session){
        return reviewService.reactOnReview(id,getLoggedId(session));
    }
}
