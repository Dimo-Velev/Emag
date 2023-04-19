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
    @NotNull
    @Positive
    private int discountPercent;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime expireDate;

}
