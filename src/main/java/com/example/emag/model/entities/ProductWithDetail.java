package com.example.emag.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "products_have_details")
public class ProductWithDetail {
    @EmbeddedId
    private ProductWithDetailKey id;
    @ManyToOne
    @MapsId("detailId")
    @JoinColumn(name = "detail_id")
    private ProductDetail productDetail;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    @Column
    private String value;
}
