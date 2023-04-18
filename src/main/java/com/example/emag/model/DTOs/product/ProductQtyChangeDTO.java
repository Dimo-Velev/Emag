package com.example.emag.model.DTOs.product;


import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductQtyChangeDTO {
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private int quantity;
}
