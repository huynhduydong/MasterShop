package com.dong.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
