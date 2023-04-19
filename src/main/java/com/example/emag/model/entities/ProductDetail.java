package com.example.emag.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "details")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

//    @ManyToMany(mappedBy = "details")
//    private Set<Product> products = new HashSet<>();


}
