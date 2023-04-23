package com.example.emag.model.DTOs.discount;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscountAddToProductDTO {
    @NotNull
    private int discountId;
    @NotNull
    private int productId;

}
