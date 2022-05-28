package com.quikdeliver.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class GoogleAuthRequest {
    private String userType;
    private String reqType;
}
