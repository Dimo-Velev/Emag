package com.example.emag.model.DTOs.order;


import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateOrderDTO {

    @Digits(integer = 1,fraction = 0, message = "PaymentId must be integer between 1 and 9.")
    private int paymentId;
    @Digits(integer = 100,fraction = 0,message = "Address id must be integer between 1 and 100.")
    private int addressId;
}

