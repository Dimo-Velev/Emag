package com.example.emag.model.DTOs.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductReactDTO {

    private String name;
    private double price;
//    private int discount;
    private double rating;
    private String message;
}
