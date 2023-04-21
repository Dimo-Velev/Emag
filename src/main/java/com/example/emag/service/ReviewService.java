package com.example.emag.service;

import com.example.emag.model.DTOs.review.*;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.Review;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ReviewService extends AbstractService {

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewWithFewInfoDTO createReviewForProduct(CreateReviewDTO dto, int id, int userId) {
        Product product = getProductById(id);
        Review review = mapper.map(dto, Review.class);
        review.setIsApproved(0);
        review.setUser(getUserById(userId));
        review.setProduct(product);
        reviewRepository.save(review);
        return mapper.map(review, ReviewWithFewInfoDTO.class);
    }

    public List<ReviewWithoutPicDTO> viewAllReviewsForProduct(int id, String rating, String sort) {
        Product product = getProductById(id);
        List<Review> reviews = product.getReviews();
        if (!rating.isEmpty()) {
            reviews = reviews.stream()
                    .filter(reviewWithoutPicDTO -> reviewWithoutPicDTO.getRating() == Integer.parseInt(rating))
                    .toList();
        }
        if (sort.equals("most-Popular")) {
            reviews = reviews.stream()
                    .sorted((r1, r2) -> r2.getLikedBy().size() - r1.getLikedBy().size())
                    .toList();
        }
        if (sort.equals("newest")) {
            reviews = reviews.stream()
                    .sorted((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()))
                    .toList();
        }
        return reviews.stream()
                .filter(review -> review.getIsApproved() == 1)
                .map(review -> mapper.map(review, ReviewWithoutPicDTO.class))
                .toList();
    }

    public List<ReviewWithoutPicAllDTO> viewAllOwnReviews(int id) {
        List<Review> reviews = userRepository.findById(id).get().getReviews();
        return reviews.stream()
                .map(review -> mapper.map(review, ReviewWithoutPicAllDTO.class))
                .toList();
    }

    public ReviewWithoutPicAllDTO editReviewOnProduct(EditReviewDTO dto, int id, int userId) {
        Review review = reviewRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new NotFoundException("Not authorized."));
        review.setHeadline(dto.getHeadline());
        review.setRating(dto.getRating());
        review.setText(dto.getText());
        review.setIsApproved(0);
        return mapper.map(reviewRepository.save(review), ReviewWithoutPicAllDTO.class);
    }

    public LikedReviewDTO reactOnReview(int id, int userId) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found."));
        User user = getUserById(userId);
        Set<Review> likesList = user.getLikes();
        LikedReviewDTO dto = mapper.map(review, LikedReviewDTO.class);
        if (likesList.stream().filter(review1 -> review1.getId() == review.getId()).findFirst().isEmpty()) {
            likesList.add(review);
            dto.setMessage("Review liked.");
        } else {
            likesList.remove(review);
            dto.setMessage("Review disliked.");
        }
        userRepository.save(user);
        return dto;
    }

    public void deleteReview(int id, int userId) {
        User user = getUserById(userId);
        Review review = getReviewById(id);
        if (!user.isAdmin() || userId != review.getUser().getId()) {
            throw new UnauthorizedException("User is not authorized to delete this review.");
        }
        reviewRepository.delete(review);
    }

    public void approveReview(int id) {
        Review review = getReviewById(id);
        review.setIsApproved(1);
        reviewRepository.save(review);
    }
}

