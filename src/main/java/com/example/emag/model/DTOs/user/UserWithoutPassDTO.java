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
public class UserWithoutPassDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String userName;
    private boolean isMale;
    private LocalDate birthdayDate;
    private LocalDateTime createdAt;
    private boolean isSubscribed;
}
