package com.quikdeliver.controller;

import com.quikdeliver.advice.exception.APIError;
import com.quikdeliver.entity.*;
import com.quikdeliver.model.AuthProvider;
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
    private final VOService voService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestParam String type, @RequestBody SignupRequest signupRequest) {
        if(userService.isUser(signupRequest.getEmail())){
            return new ResponseEntity<>(new APIError().addCommonError("Already registered gmail."),HttpStatus.NOT_ACCEPTABLE);
        }
        User user;
        switch (type) {
            case "customer":
                Customer customer = new Customer();
                customer.setName(signupRequest.getName());
                customer.setEmail(signupRequest.getEmail());
                customer.setPassword(signupRequest.getPassword());
                customer.setRoles(Arrays.asList(userService.getRole("ROLE_CUSTOMER")));
                customer.setProvider(AuthProvider.local);
                user = customerService.saveCustomer(customer);
                break;
            case "driver":
                Driver driver = new Driver();
                driver.setName(signupRequest.getName());
                driver.setEmail(signupRequest.getEmail());
                driver.setPassword(signupRequest.getPassword());
                driver.setRoles(Arrays.asList(userService.getRole("ROLE_DRIVER")));
                driver.setProvider(AuthProvider.local);
                user = driverService.saveDriver(driver);
                break;
            case "vo":
                VehicleOwner vo = new VehicleOwner();
                vo.setName(signupRequest.getName());
                vo.setEmail(signupRequest.getEmail());
                vo.setPassword(signupRequest.getPassword());
                vo.setRoles(Arrays.asList(userService.getRole("ROLE_VO")));
                vo.setProvider(AuthProvider.local);
                user = voService.saveVO(vo);
                break;
            default:
                return new ResponseEntity<>(new APIError().addCommonError("Invalid type or invalid URL."),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
}
