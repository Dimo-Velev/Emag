package com.example.emag.model.DTOs.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductInCartDTO {

    private String name;
    private int quantity;
    private double price;
    private String discount;
}
