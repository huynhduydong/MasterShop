package com.dong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role  extends BaseEntity{

    @Column(unique = true, nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 150)
    private String description;

    public Role(int id) {
        this.id = id;
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
