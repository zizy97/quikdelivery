package com.quikdeliver.service;

import com.quikdeliver.entity.Role;
import com.quikdeliver.entity.User;

import java.util.List;

public interface UserService {
    public User saveUser(User user);
    public User saveUser(User user,String type);
    public User getUser(Long id);
    public User getUser(String email);
    public boolean isUser(String email);
    public List<User> getAllUsers();
    public Role saveRole(Role role);
    public void addRoleToUser(String username, String role);
    public Role getRole(String name);
    public boolean isRolesSet();
}
