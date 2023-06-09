package com.example.emag.model.DTOs.review;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateReviewDTO {

    @Min(value = 1,message = "Rating must be between 1 and 5.")
    @Max(value = 5,message = "Rating must be between 1 and 5.")
    @NotNull(message = "Rating cannot be null.")
    private int rating;
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Headline must have only characters.")
    @NotNull(message = "Headline cannot be null.")
    private String headline;
    @NotNull(message = "Review cannot be null.")
    private String text;

}
