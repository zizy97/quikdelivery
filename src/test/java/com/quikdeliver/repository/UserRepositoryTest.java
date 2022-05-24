package com.quikdeliver.repository;

import com.quikdeliver.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void findByEmail() {
        User byEmail = userRepository.findByEmail("abc@gmail.com").get();
        if(byEmail != null) {
            System.out.println(byEmail.getEmail());
        }
    }
}