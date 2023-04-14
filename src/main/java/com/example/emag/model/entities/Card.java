package com.example.emag.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String cardNumber;

    @Column
    private LocalDate expireDate;

    @Column
    private String cvv2Cvc2Code; //TODO change name

    @Column
    private Integer userId;}
