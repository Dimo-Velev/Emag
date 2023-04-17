package com.example.emag.model.DTOs.order;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CreatedOrderDTO {

    private List<ProductOrderDTO> products;
    private String name;
    private LocalDateTime createdAt;
    private double totalPrice;
}
