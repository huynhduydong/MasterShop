package com.dong.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipping_method")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ShippingMethod extends BaseEntity {

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false)
    private double price;
}
