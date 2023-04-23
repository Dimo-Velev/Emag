package com.example.emag.model.DTOs.product;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductQuantityDTO {

    @NotNull(message = "Quantity cannot be null")
    @Digits(integer = 100, fraction = 0, message = "Product quantity must be between 1 and 100 with no floating point.")
    private int quantity;
}
