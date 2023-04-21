package com.example.emag.model.DTOs.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductEditDTO {
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.!?'\"-]*$", message = "Product name cannot contain special characters")
    @Size(min = 1, max = 255, message = "Product name must be between {min} and {max} characters")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.!?'\"-]*$", message = "Product description cannot contain special characters")
    private String description;
    @Positive(message = "Price must be positive")
    private double price;
    @NotNull(message = "Category cannot be null")
    private int categoryId;
}
