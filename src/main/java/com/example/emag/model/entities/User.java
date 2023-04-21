package com.example.emag.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<Address> addresses;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorite_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> favoriteProducts;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<CartContent> productsInCart;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Review> reviews;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reviews_have_likes",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Review> likes;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_viewed_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> viewedProducts;
}
