package com.example.emag.model.DTOs.orderContent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderContentDTO {

    private String name;
    private int quantity;
}
