package com.example.emag.model.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @NotNull
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "seller_id", nullable = false)
//    private Seller seller;

}
