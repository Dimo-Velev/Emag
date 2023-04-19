package com.example.emag.model.DTOs.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class EditProfileDTO {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Pattern(regexp = "^\\d{10}$", message = "Incorrect phone number format") //must be 10 digits
    private String phoneNumber;
    private String userName;
    private Boolean isMale;
    private LocalDate birthdayDate;

}
