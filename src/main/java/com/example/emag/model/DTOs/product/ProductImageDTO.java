package com.example.emag.model.DTOs.product;

import com.example.emag.model.DTOs.product.ProductViewDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO {
    private int id;
    private String pictureUrl;
    private ProductViewDTO product;
}
