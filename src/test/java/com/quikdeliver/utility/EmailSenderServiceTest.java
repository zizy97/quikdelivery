package com.quikdeliver.utility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailSenderServiceTest {
    @Autowired
    private EmailSenderService emailSenderService;
    @Test
    void sendSimpleMail() {
        emailSenderService.sendSimpleMail("supuntharuka9749@gmail.com","This is sample body","sample mail");
    }
}