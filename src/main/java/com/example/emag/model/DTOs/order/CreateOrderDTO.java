package com.example.emag.model.DTOs.order;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateOrderDTO {

    @NotNull(message = "Payment id cant be null.")
    @Digits(integer = 1,fraction = 0, message = "PaymentId must be integer between 1 and 2.")
    private int paymentId;
    @NotNull(message = "Address id cant be null.")
    @Digits(integer = 100,fraction = 0,message = "Address id must be integer between 1 and 100.")
    private int addressId;
}

