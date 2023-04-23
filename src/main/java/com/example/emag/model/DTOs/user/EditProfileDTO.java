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
    @Pattern(regexp = "^[a-zA-Z]*$",message = "Last name can contain only letters")
    private String firstName;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]*$",message = "Last name can contain only letters")
    private String lastName;
    @Pattern(regexp = "^08[789]\\d{7}$", message = "Phone number should start with 08 and be 10 numbers.")
    private String phoneNumber;
    private String userName;
    private Boolean isMale;
    private LocalDate birthdayDate;

}
