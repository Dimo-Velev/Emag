package com.example.emag.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private String phoneNumber;
    @Column
    private String address;
    @Column
    private String residentialArea;
    @Column
    private int floor;
//    @Column
//    private User user;
    @Column
    private String region;
    @Column
    private String city;
}
