package com.example.emag.model.DTOs.product;

import com.example.emag.model.DTOs.discount.DiscountViewDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductViewDTO {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private DiscountViewDTO discount;

}
