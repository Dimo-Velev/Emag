package com.example.emag.model.DTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CreatedOrderDTO {

    private List<ProductOrderDTO> products;
    private String name;
    private double totalPrice;
}
