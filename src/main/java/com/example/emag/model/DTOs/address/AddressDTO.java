package com.example.emag.model.DTOs.address;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddressDTO {

    @NotNull(message = "Name cannot be null")
    @Pattern(regexp = "^[A-Za-z]+ [A-Za-z]+$", message = "Names should be letters only, separated with space.")
    private String name;
    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^08[789]\\d{7}$", message = "Phone number should start with 08 and be 10 numbers.")
    private String phoneNumber;
    @NotNull(message = "Address cannot be null")
    @Pattern(regexp = "^(?:[a-zA-Z0-9\\s]+|[\\u0410-\\u044F0-9\\s]+)$", message = "Address must contain only letters,digits,and whitespaces.")
    private String address;
    @NotNull(message = "Residential area cannot be null")
    @Pattern(regexp = "^(?:[a-zA-Z0-9\\s]+|[\\u0410-\\u044F0-9\\s]+)$", message = "Residential area must contain only letters,digits,and whitespaces.")
    private String residentialArea;
    @NotNull(message = "Floor cannot be null")
    @Min(value = 0, message = "Not a valid building floor, needs to be between 0 and 30.")
    @Max(value = 30, message = "Not a valid building floor, needs to be between 0 and 30.")
    @Pattern(regexp = ".*\\d+.*", message = "Floor needs to be a digit.")
    private String floor;

}
