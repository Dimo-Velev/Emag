package com.example.emag.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private double price;

    @Column
    private LocalDateTime createdAt;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "status_id", nullable = false)
//    private OrderStatus status;
//
//    @ManyToOne
//    @JoinColumn(name = "payment_type_id", nullable = false)
//    private PaymentType paymentType;

}
