package com.example.emag.model.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "discounts")
public class Discount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column
    private int discountPercent;

    @NotNull
    @Column
    private LocalDateTime startDate;

    @NotNull
    @Column
    private LocalDateTime expireDate;

//    @OneToMany(mappedBy = "discount_id")
//    private List<Product> discountProducts;


}
