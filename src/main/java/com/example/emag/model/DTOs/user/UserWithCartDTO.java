package com.example.emag.model.DTOs.user;

import com.example.emag.model.DTOs.cart.CartContentDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserWithCartDTO {

    private int userId;
    private CartContentDTO cartContentDTO;
}
