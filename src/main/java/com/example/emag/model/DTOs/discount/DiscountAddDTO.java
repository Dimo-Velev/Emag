package com.example.emag.model.DTOs.discount;

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
    @Positive(message = "Discount percent should be positive")
    private int discountPercent;
    @NotNull(message = "Category cannot be null")
    private LocalDateTime startDate;
    @NotNull(message = "Category cannot be null")
    private LocalDateTime expireDate;

}
