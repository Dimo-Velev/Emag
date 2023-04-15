package com.example.emag.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private double price;

    @Column
    private int quantity;

//    @Column
//    @JoinColumn(name = "discount_id")
//    private Discount discount;
//
//    @Column
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;
//
//    @Column
//    @JoinColumn(name = "seller_id", nullable = false)
//    private Seller seller;

}
