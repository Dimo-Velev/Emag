package com.example.emag.model.DTOs.order;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateOrderDTO {

    private int paymentId;
    private int addressId;
}

