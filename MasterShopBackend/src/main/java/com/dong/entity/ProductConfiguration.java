package com.dong.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_configuration")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductConfiguration extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

    @ManyToOne
    @JoinColumn(name = "variation_option_id", nullable = false)
    private VariationOption variationOption;
}
