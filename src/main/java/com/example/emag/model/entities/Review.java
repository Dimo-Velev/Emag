package com.example.emag.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private int rating;

    @Column
    private String headline;

    @Column
    private String text;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String pictureUrl;

    @Column
    private int userId;

    @Column
    private int productId;

    @Column
    private int isApproved;

}
