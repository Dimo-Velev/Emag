package com.example.emag.model.DTOs.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderAddressDTO {

    private String name;
    private String phoneNumber;
    private String address;
    private String residentialArea;
    private int floor;
}
