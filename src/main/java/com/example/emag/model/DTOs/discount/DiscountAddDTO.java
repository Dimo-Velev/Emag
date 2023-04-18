package com.example.emag.model.DTOs.discount;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DiscountAddDTO {
    @NotNull
    private int discountPercent;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime expireDate;

}
