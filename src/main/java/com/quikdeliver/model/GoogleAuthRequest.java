package com.quikdeliver.model;

import com.quikdeliver.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class GoogleAuthRequest {
    private String userType;
    private String reqType;
    private User user;
}
