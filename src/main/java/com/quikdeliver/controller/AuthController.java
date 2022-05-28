package com.quikdeliver.controller;

import com.quikdeliver.entity.Customer;
import com.quikdeliver.entity.Driver;
import com.quikdeliver.entity.Role;
import com.quikdeliver.entity.VehicleOwner;
import com.quikdeliver.model.SignupRequest;
import com.quikdeliver.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CustomerService customerService;
    private final DriverService driverService;
    private final VOServiceImpl VOServiceImpl;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestParam String type, @RequestBody SignupRequest signupRequest) {
        switch (type) {
            case "customer":

                return new ResponseEntity<>(customerService.saveCustomer(
                        new Customer(signupRequest.getEmail(),
                                signupRequest.getPassword(),
                                signupRequest.getName(),
                                Arrays.asList(userService.getRole("ROLE_CUSTOMER")))), HttpStatus.OK);
            case "driver":
                return new ResponseEntity<>(driverService.saveDriver(new Driver(signupRequest.getEmail(),
                        signupRequest.getPassword(),
                        signupRequest.getName(),
                        Arrays.asList(userService.getRole("ROLE_DRIVER")))),HttpStatus.OK);
            case "vehicleOwner":
                return new ResponseEntity<>(VOServiceImpl.saveVO(new VehicleOwner(signupRequest.getEmail(),
                        signupRequest.getPassword(),
                        signupRequest.getName(),
                        Arrays.asList(userService.getRole("ROLE_VO")))),HttpStatus.OK);
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
