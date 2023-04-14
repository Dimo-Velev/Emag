package com.example.emag.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_types")
public class PaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
