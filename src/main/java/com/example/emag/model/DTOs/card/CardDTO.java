package com.example.emag.model.DTOs.card;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
public class CardDTO {

    @Pattern(regexp = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|(5[06-8]|6[37])[0-9]{12,15})$", message = "Not a valid card or not a supported type.")
    private String cardNumber;
    @Range(min = 1, max = 12, message = "Not a valid month, months are between 1 and 12")
    private int expireMonth;
    @Pattern(regexp = "^20[2-9][3-9]$", message = "Not a valid year, must be between 2029 and 2099.")
    private int expireYear;
    @Pattern(regexp = "^[0-9]{3,4}$", message = "CVV code must be a 3 or 4-digit number")
    private int cvvCode;
    private String name;

}
