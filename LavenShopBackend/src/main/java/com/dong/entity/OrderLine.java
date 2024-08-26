package com.dong.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_line")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderLine extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private ShopOrder order;

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private double price;
}
