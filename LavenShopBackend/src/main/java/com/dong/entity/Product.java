package com.dong.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Product extends BaseEntity {

    private String name;
    private String brand;

    @Column(length = 5000)
    private String description;
    private double price;
    private double discountRate;
    private String thumbnailUrl;
    private int reviewCount;
    private double ratingAverage;
    private int quantitySold;
    private String productSlug;
    private String categoryUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> options = new ArrayList<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductItem> productItems = new HashSet<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductSpecification> specifications = new HashSet<>();
}
