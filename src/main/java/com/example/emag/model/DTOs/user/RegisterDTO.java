package com.example.emag.model.DTOs.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class RegisterDTO {

    @NotNull(message = "First name cannot be null.")
    private String firstName;
    @NotNull(message = "Last name cannot be null.")
    private String lastName;
    @Email
    @NotNull(message = "Email cannot be null.")
    private String email;
    @NotNull(message = "Password name cannot be null.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;
    @NotNull(message = "Confirm password name cannot be null.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String confirmPassword;
    private String userName;

}
