package com.dong.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductItem extends BaseEntity {



    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference

    private Product product;

    @Column(nullable = false, length = 64)
    private String sku;

    @Column(name = "qty_in_stock", nullable = false)
    private Integer qtyInStock;

    @Column(name = "product_image", length = 255)
    private String productImage;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private float cost;
}
