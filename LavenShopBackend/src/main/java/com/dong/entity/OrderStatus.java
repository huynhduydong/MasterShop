package com.dong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_status")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderStatus extends BaseEntity {

    @Column(nullable = false, length = 64)
    private String status;
}
