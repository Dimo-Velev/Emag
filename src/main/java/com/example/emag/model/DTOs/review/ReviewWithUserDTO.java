package com.example.emag.model.DTOs.review;

import com.example.emag.model.DTOs.user.UserWithFewInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ReviewWithUserDTO {

    private int id;
    private int rating;
    private String headline;
    private String text;
    private String pictureUrl;
    private LocalDateTime createdAt;
    private UserWithFewInfo user;
}
