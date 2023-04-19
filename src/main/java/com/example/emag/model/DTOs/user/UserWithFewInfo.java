package com.example.emag.model.DTOs.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class UserWithFewInfo {

    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private LocalDateTime createdAt;

}
