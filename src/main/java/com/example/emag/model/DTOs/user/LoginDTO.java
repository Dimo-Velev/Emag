package com.example.emag.model.DTOs.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {
    private String email;
    private String password;

}
