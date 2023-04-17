package com.example.emag.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column
    private String userName;
    @Column(name = "is_male")
    private boolean isMale;
    @Column(name = "birthday_date")
    private LocalDate birthdayDate;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "is_admin")
    private boolean isAdmin;
    @Column(name = "is_subscribed")
    private boolean isSubscribed;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Card> cards;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Card> addresses;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorite_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> favoriteProducts;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<CartContent> productsInCart;
}
