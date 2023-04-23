package com.example.emag.model.DTOs.card;

import com.bol.secure.Encrypted;
import jakarta.validation.constraints.Digits;
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

    @Encrypted
    @Pattern(regexp = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|(5[06-8]|6[37])[0-9]{12,15})$", message = "Not a valid card or not a supported type.")
    private String cardNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])$", message = "Not a valid month, months are between 01 and 12")
    @Encrypted
    private String expireMonth;
    @Encrypted
    @Pattern(regexp = "^(202[3-9]|[2-9]\\d{3})$", message = "Not a valid year, must be between 2023 and 2099")
    private String expireYear;
    @Encrypted
    @Pattern(regexp = "^\\d{3}$", message = "CVV code must be a 3 digit number.")
    private String cvvCode;
    @Encrypted
    @Pattern(regexp = "^[A-Z]+ [A-Z]+$", message = "Not a valid name, Name must be first and last all capital letters, separated by space.")
    private String name;

}
