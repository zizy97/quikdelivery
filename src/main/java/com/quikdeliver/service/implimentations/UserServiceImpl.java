package com.quikdeliver.service.implimentations;

import com.quikdeliver.entity.*;
import com.quikdeliver.repository.*;
import com.quikdeliver.model.UserPrincipal;
import com.quikdeliver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final VORepository voRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();
        if (user == null) {
            log.error("User with email-{} not found",email);
            throw new UsernameNotFoundException("User with email- " + email + " not found");
        }
        log.info("User with email-{} found",email);

        return UserPrincipal.create(user);
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
    public boolean isUser(String email) {
        return userRepository.existsUserByEmail(email);
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
    public User saveUser(User user,String type) {
        log.info("Saving new user to database");
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
        switch (roleName){
            case "ROLE_CUSTOMER":
                customerRepository.updateNewRecord(user.getId());
                break;
            case "ROLE_DRIVER":
                driverRepository.updateNewRecord(user.getId());
                break;
            case "ROLE_VO":
                voRepository.updateNewRecord(user.getId());
                break;
        }
        log.info("Adding new role-{} to email-{}",roleName,email);
    }

    @Override
    public Role getRole(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public boolean isRolesSet() {
        return roleRepository.count()==4;
    }

}
