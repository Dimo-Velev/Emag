package com.example.emag.model.DTOs.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CartContentDTO {

    private List<ProductInCartDTO> products;
    private double totalPrice;
}
