package com.example.emag.model.DTOs.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {

    @Email
    @NotNull(message = "Email cannot be null.")
    private String email;
    @NotNull(message = "Password cannot be null.")
    private String password;

}
