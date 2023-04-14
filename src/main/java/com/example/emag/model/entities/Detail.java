package com.example.emag.model.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "details")
@Table(name = "details")
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "details")
    private Set<Product> products = new HashSet<>();


}
