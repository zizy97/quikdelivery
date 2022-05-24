package com.quikdeliver.service;

import com.quikdeliver.entity.Role;
import com.quikdeliver.entity.User;
import com.quikdeliver.repository.RoleRepository;
import com.quikdeliver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();
        if (user == null) {
            log.error("User with email-{} not found",email);
            throw new UsernameNotFoundException("User with email- " + email + " not found");
        }else{
            log.info("User with email-{} found",email);
        }
        List<SimpleGrantedAuthority> authorities= user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),authorities);
    }

    @Override
    public User getUser(Long id) {
        log.info("Getting user with id-{}",id);
        return userRepository.findById(id).get();
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user to database");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role to database");
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email).get();
        Role role = roleRepository.findRoleByName(roleName);
        user.getRoles().add(role);
        log.info("Adding new role-{} to email-{}",roleName,email);
    }

}
