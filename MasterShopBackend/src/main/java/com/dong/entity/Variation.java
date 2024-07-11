package com.dong.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "variation")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Variation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, length = 128)
    private String name;
}
