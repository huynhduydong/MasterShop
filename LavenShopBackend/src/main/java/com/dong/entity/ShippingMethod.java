package com.dong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shipping_method")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ShippingMethod extends BaseEntity {

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false)
    private double price;
}
