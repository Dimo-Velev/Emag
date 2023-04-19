package com.example.emag.model.DTOs.review;

import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.model.DTOs.user.UserWithFewInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ReviewWithoutPicDTO {

    private int id;
    private int rating;
    private String headline;
    private String text;
    private LocalDateTime createdAt;
    private UserWithFewInfo user;
    private ProductViewDTO product;
}
