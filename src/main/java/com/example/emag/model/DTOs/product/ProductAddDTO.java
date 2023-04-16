package com.example.emag.model.DTOs.product;


import com.example.emag.model.entities.Category;
import com.example.emag.model.entities.Seller;
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
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Product name can contain only letters and digits")
    @Size(min = 1, max = 255, message = "Product name must be between {min} and {max} characters")
    private String name;
    @Nullable
    //TODO change validation to include spaces
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Product description can contain only letters and digits")
    private String description;
    @DecimalMin(value = "0.0", message = "Price must be positive")
    private double price;
    @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
    private int quantity;

    //TODO add product CategoryNameDTO, some sellerDTO
//    private int category;
//    private int seller;

}
