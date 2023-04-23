package com.example.emag.model.DTOs.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddressIdDTO {

    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private String residentialArea;
    private String floor;
}
