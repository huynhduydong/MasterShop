package com.dong.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
