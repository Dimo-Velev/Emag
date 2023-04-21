package com.example.emag.model.DTOs.order;


import com.example.emag.model.DTOs.address.OrderAddressDTO;
import com.example.emag.model.DTOs.orderContent.OrderContentDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class CreatedOrderDTO {

    private int id;
    private Set<OrderContentDTO> products;
    private String name;
    private LocalDateTime createdAt;
    private double totalPrice;
    private OrderAddressDTO address;
}
