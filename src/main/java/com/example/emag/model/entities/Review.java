package com.example.emag.model.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private Integer rating;

    @Column
    private String headline;

    @Column
    private String text;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String pictureUrl;

    @Column
    private Integer userId;

    @Column
    private Integer productId;

    @Column
    private Integer isApproved;

}
