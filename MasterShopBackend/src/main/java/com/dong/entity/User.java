package com.dong.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="USER")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    @Column( name = "username")
    private String username;
    @Column(nullable = false, unique = true, length = 128)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Column(length = 64)
    private String photos;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles"
            , joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id") )
    private Set<Role> roles = new HashSet<>();

    public User(String email, String password,String username, String firstName, String lastName, String photo, boolean enabled, Set<Role> roles) {
        this.email = email;
        this.username= username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photos = photo;
        this.enabled = enabled;
        this.roles = roles;
    }
}
