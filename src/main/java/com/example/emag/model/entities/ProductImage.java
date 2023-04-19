package com.example.emag.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "products_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String pictureUrl;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
