package com.dong.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shopping_cart_item")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ShoppingCartItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart cart;

    @ManyToOne
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

    @Column(nullable = false)
    private int qty;
}
