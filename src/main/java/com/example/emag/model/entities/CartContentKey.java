package com.example.emag.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CartContentKey implements Serializable {

    @Column(name = "user_id")
    private int userId;
    @Column(name = "product_id")
    private int productId;
}
