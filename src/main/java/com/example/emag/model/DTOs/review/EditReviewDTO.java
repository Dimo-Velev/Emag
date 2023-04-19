package com.example.emag.model.DTOs.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EditReviewDTO {

    @Min(value = 1,message = "Rating must be between 1 and 5.")
    @Max(value = 5,message = "Rating must be between 1 and 5.")
    @NotNull(message = "Rating is required.")
    private int rating;
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Headline must have only characters.")
    @NotNull(message = "Headline is required.")
    private String headline;
    @NotNull(message = "Review text is required.")
    private String text;
}
