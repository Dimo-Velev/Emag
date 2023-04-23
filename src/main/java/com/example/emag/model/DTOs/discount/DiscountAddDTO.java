package com.example.emag.model.DTOs.discount;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DiscountAddDTO {
    @NotNull(message = "Discount percent cannot be null")
    @Positive(message = "Discount percent should be positive")
    @Min(value = 1,message = "Discount percent should be between 1 and 100.")
    @Max(value = 100,message = "Discount percent should be between 1 and 100.")
    private int discountPercent;
    @NotNull(message = "Start date cannot be null")
    private LocalDateTime startDate;
    @NotNull(message = "Expire date cannot be null")
    private LocalDateTime expireDate;

}
