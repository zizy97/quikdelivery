package com.quikdeliver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quikdeliver.model.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users",
uniqueConstraints = {
        @UniqueConstraint(columnNames={"email"})
})
public class User {

    public User(String email, String password, Collection<Role> roles) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public User( String email, String password, String name, Collection<Role> roles,AuthProvider provider) {
        this.password = password;
        this.name=name;
        this.email = email;
        this.roles = roles;
        this.provider=provider;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true,updatable = false)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;


    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles = new ArrayList<>();

    @Column(columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    //todo:need to delete
    private String nic;
}
