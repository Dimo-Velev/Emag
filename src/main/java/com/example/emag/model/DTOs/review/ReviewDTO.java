package com.example.emag.model.DTOs.review;

import com.example.emag.model.DTOs.product.ProductViewDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ReviewDTO {

    private int id;
    private int rating;
    private String headline;
    private String text;
    private String pictureUrl;
    private LocalDateTime createdAt;
    private int isApproved;
    private ProductViewDTO product;
}
