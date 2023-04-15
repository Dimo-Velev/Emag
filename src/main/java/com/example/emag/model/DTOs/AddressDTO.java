package com.example.emag.model.DTOs;


import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Setter
@Getter
@NoArgsConstructor
public class AddressDTO {

    @Pattern(regexp = "^[A-Za-z]+ [A-Za-z]+$", message = "Names should be letters only.")
    private String name;
    @Pattern(regexp = "^08[789]\\d{7}$", message = "Phone number should start with 08 and be 10 numbers.")
    private String phoneNumber;
    @Pattern(regexp = "^(?:[a-zA-Z0-9\\s]+|[\\u0410-\\u044F0-9\\s]+)$", message = "Address must contain only letters,digits,and whitespaces.")
    private String address;
    @Pattern(regexp = "^(?:[a-zA-Z0-9\\s]+|[\\u0410-\\u044F0-9\\s]+)$", message = "Residential area must contain only letters,digits,and whitespaces.")
    private String residentialArea;
    @Range(min = 0, max = 30,message = "Not a valid building floor.")
    private int floor;

}
