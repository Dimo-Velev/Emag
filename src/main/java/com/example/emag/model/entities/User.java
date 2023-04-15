package com.example.emag.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String phoneNumber;

    @Column
    private String userName;

    @Column
    private boolean isMale;

    @Column
    private LocalDate birthdayDate;

    @Column
    private LocalDateTime createdAt;

    @Column
    private boolean isAdmin;

    @Column
    private boolean isSubscribed;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Card> cards;
}
