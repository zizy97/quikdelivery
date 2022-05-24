package com.quikdeliver.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User(Long id, String email, String password, Collection<Role> roles) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    @NotNull
    private String nic;

    private String password;
    private String firstName;
    private String lastName;
    private String email;

    private String phone;

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
}
