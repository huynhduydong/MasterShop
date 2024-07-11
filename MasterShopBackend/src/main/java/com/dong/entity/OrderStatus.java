package com.dong.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_status")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderStatus extends BaseEntity {

    @Column(nullable = false, length = 64)
    private String status;
}
