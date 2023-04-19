package com.example.emag.model.DTOs.order;

import com.example.emag.model.entities.Address;
import com.example.emag.model.entities.OrderStatus;
import com.example.emag.model.entities.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
public class OrderWithFewInfoDTO {

    private double price;
    private LocalDateTime createdAt;
    private OrderStatus orderStatus;
    private PaymentType paymentType;
    private Address address;
}
