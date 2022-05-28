package com.quikdeliver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    private String name;
    @NotBlank(message = "Name is mandatory")
    private String email;
    @NotBlank(message = "Name is mandatory")
    private String password;
}
