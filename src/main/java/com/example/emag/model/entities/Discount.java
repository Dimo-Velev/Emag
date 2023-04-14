package com.example.emag.model.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
public class Discount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount_percent", nullable = false)
    private Integer discountPercent;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expireDate;

}
