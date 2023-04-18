package com.example.emag.model.DTOs.discount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DiscountViewDTO {
    private long id;
    private int discountPercent;
    private LocalDateTime startDate;
    private LocalDateTime expireDate;
}
