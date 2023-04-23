package com.example.emag.model.DTOs.card;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CardWithFewInfoDTO {

    private int id;
    private String cardNumber;
    private String expireMonth;
    private String expireYear;
    private String name;
    private String cvv;

}
