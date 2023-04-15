package com.example.emag.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "discounts")
public class Discount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "discount_percent", nullable = false)
    private int discountPercent;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expireDate;

}
