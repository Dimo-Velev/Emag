package com.example.emag.service;

import com.example.emag.model.DTOs.review.*;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.Review;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        review.setPictureUrl("No picture yet.");
        return mapper.map(review, ReviewWithFewInfoDTO.class);
    }

    public Page<ReviewWithUserDTO> viewAllReviewsForProduct(int id, String rating, String sort, Pageable pageable) {
        Product product = getProductById(id);
        List<Review> reviews = product.getReviews();
        if (rating != null && !rating.isEmpty()) {
            reviews = reviews.stream()
                    .filter(reviewWithoutPicDTO -> reviewWithoutPicDTO.getRating() == Integer.parseInt(rating))
                    .toList();
        }
        if (sort != null && sort.equals("most-Popular")) {
            reviews = reviews.stream()
                    .sorted((r1, r2) -> r2.getLikedBy().size() - r1.getLikedBy().size())
                    .toList();
        }
        if (sort != null && sort.equals("newest")) {
            reviews = reviews.stream()
                    .sorted((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()))
                    .toList();
        }
        List<ReviewWithUserDTO> reviewDTOs = reviews.stream()
                .filter(review -> review.getIsApproved() == 1)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(review -> mapper.map(review, ReviewWithUserDTO.class))
                .toList();
        return new PageImpl<>(reviewDTOs, pageable, reviewDTOs.size());
    }

    public List<ReviewDTO> viewAllOwnReviews(int id) {
        List<Review> reviews = userRepository.findById(id).get().getReviews();
        return reviews.stream()
                .map(review -> mapper.map(review, ReviewDTO.class))
                .toList();
    }

    public ReviewDTO editReviewOnProduct(EditReviewDTO dto, int id, int userId) {
        Review review = reviewRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new NotFoundException("Not authorized."));
        review.setHeadline(dto.getHeadline());
        review.setRating(dto.getRating());
        review.setText(dto.getText());
        review.setIsApproved(0);
        return mapper.map(reviewRepository.save(review), ReviewDTO.class);
    }

    public LikedReviewDTO reactOnReview(int id, int userId) {
        Review review = reviewRepository.findByIdAndIsApprovedTrue(id).orElseThrow(() -> new NotFoundException("Review not found or not approved yet."));
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
    public Page<ReviewDTO> getAllUnapprovedReviews(Pageable pageable) {
        Page<Review> unapprovedReviews = reviewRepository.findByIsApprovedFalse(pageable);
        return unapprovedReviews.map(unapprovedReview-> mapper.map(unapprovedReview, ReviewDTO.class));
    }
}

