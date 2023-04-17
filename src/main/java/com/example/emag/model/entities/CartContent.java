package com.example.emag.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "products_in_cart")
public class CartContent {

    @EmbeddedId
    private CartContentKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;
}
