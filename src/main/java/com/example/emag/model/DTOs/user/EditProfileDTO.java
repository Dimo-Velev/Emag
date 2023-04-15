package com.example.emag.model.DTOs.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class EditProfileDTO {

    private String firstName;
    private String lastName;
    @Email
    private String email;
    @Pattern(regexp = "^\\d{10}$", message = "Incorrect phone number format")
    private String phoneNumber;
    private String userName;
    private boolean isMale;
    private LocalDate birthdayDate;
    private boolean isSubscribed;

}
