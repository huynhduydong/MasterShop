package com.dong.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "variation_option")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VariationOption extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "variation_id", nullable = false)
    private Variation variation;

    @Column(nullable = false, length = 128)
    private String value;
}
