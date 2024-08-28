package com.dong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Category extends BaseEntity {

    private String name;

    @Column(unique = true)
    private String urlKey;
    private String thumbnailUrl;
    private Long parentId;
    private boolean isPrimary;

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();
}
