package com.example.emag.model.DTOs.product;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductAddDTO {


    @Pattern(regexp = "^[a-zA-Z0-9\\s,.!?'\"-]*$", message = "Product name cannot contain special characters")
    @Size(min = 1, max = 255, message = "Product name must be between {min} and {max} characters")
    private String name;
    @Nullable
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.!?'\"-]*$", message = "Product description cannot contain special characters")
    private String description;
    @Positive(message = "Price must be positive")
    private double price;
    @Positive(message = "Quantity must be positive")
    private int quantity;
    @NotNull
    private int categoryId;

}
