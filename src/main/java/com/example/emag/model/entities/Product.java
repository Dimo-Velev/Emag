package com.example.emag.model.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private Integer quantity;

    @Column
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Column
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

}
