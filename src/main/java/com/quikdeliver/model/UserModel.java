package com.quikdeliver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
public
class UserModel{
    private String email;
    private Collection<SimpleGrantedAuthority> authorities;
}