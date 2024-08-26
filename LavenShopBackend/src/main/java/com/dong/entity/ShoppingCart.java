package com.dong.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shopping_cart")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ShoppingCart extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
