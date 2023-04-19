package com.example.emag.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductWithDetailKey implements Serializable {
    @Column(name = "detail_id")
    private int detailId;
    @Column(name = "product_id")
    private int productId;

}
