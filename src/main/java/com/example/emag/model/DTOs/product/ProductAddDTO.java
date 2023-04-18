package com.example.emag.model.DTOs.product;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductAddDTO {


    //TODO change validation to include spaces
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.!?'\"-]*$", message = "Product name cannot contain special characters")
    @Size(min = 1, max = 255, message = "Product name must be between {min} and {max} characters")
    private String name;
    @Nullable
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.!?'\"-]*$", message = "Product description cannot contain special characters")
    private String description;
    @DecimalMin(value = "0.0", message = "Price must be positive")
    private double price;
    @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
    private int quantity;

    // TODO
//    private int category;

}
