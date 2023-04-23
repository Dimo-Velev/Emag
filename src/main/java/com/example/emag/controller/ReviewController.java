package com.example.emag.controller;

import com.example.emag.model.DTOs.review.*;
import com.example.emag.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController extends AbstractController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("products/{id:\\d+}/reviews")
    public ReviewWithFewInfoDTO createForProduct(@Valid @RequestBody CreateReviewDTO dto,
                                                 @Valid @PathVariable int id,
                                                 HttpSession session){
        return reviewService.createReviewForProduct(dto,id,getLoggedId(session));
    }
    @GetMapping("/products/{id:\\d+}/reviews")
    public Page<ReviewWithUserDTO> viewAllForProduct(@PathVariable int id,
                                                     @RequestParam(required = false, name = "rating")
                                                       @Pattern(regexp = "^[1-5]$",
                                                               message = "View by rating has to be between 1 and 5") String rating,
                                                     @RequestParam(required = false, name = "sort")
                                                       @Pattern(regexp = "newest|most-Popular",
                                                               message = "View by date has to be newest or most-Popular") String sort,
                                                     @PageableDefault(size = 10) Pageable pageable){
        return reviewService.viewAllReviewsForProduct(id,rating,sort,pageable);
    }
    @GetMapping("/reviews")
    public List<ReviewDTO> viewAllOwn(HttpSession session) {
        return reviewService.viewAllOwnReviews(getLoggedId(session));
    }
    @PutMapping("/reviews/{id:\\d+}")
    public ReviewDTO edit(@Valid @RequestBody EditReviewDTO dto, @PathVariable int id, HttpSession session){
        return reviewService.editReviewOnProduct(dto,id,getLoggedId(session));
    }
    @PutMapping("/reviews/{id:\\d+}/react")
    public LikedReviewDTO likeDislikeReview(@PathVariable int id, HttpSession session){
        return reviewService.reactOnReview(id,getLoggedId(session));
    }
    @DeleteMapping("/reviews/{id:\\d+}")
    public ResponseEntity<String> delete(@PathVariable int id, HttpSession session){
        reviewService.deleteReview(id,getLoggedId(session));
        return ResponseEntity.ok("Review deleted successfully");
    }
    @PutMapping("/reviews/{id:\\d+}/approve")
    public ResponseEntity<String> approveByAdmin(@PathVariable int id,HttpSession session){
        isLoggedAdmin(session);
        reviewService.approveReview(id);
        return ResponseEntity.ok("Review was approved.");
    }
    @GetMapping("/reviews/pending")
    public Page<ReviewDTO> getAllUnapprovedReviews(@PageableDefault(size = 10) Pageable pageable, HttpSession session) {
        isLoggedAdmin(session);
        return reviewService.getAllUnapprovedReviews(pageable);
    }
}
