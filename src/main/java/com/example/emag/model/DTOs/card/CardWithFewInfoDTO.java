package com.example.emag.model.DTOs.card;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CardWithFewInfoDTO {

    private int id;
    private String maskedCardNumber;
    private int expireMonth;
    private int expireYear;
    private String name;

}
