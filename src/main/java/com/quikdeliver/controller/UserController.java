package com.quikdeliver.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quikdeliver.entity.Role;
import com.quikdeliver.entity.User;
import com.quikdeliver.model.TokensType;
import com.quikdeliver.service.UserService;
import com.quikdeliver.utility.JWTHandler;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final JWTHandler jwtHandler;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        List<Role> roleList = userForm.getRoles().stream().map(r->new Role(r)).collect(Collectors.toList());
        user.setRoles(roleList);
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<User> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getEmail(),form.getRole());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/whoami")
    public ResponseEntity<String> getUserByAuth(){
        log.info("Whoami");
        String userEmail = jwtHandler.getUserEmail();
        return ResponseEntity.ok().body(userEmail);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = jwtHandler.getToken();
        if(refreshToken != null){
                try{
                    String userEmail = jwtHandler.getUserEmail();
                    User user = userService.getUser(userEmail);
                    Map<String,String > tokens = jwtHandler.buildNewToken(TokensType.ACCESS,user.getEmail(),request.getRequestURL().toString(),user.getRoles());
                    tokens.put("refresh", refreshToken);
                    response.setContentType(APPLICATION_JSON_VALUE); // Content type
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                }catch (JWTVerificationException | IOException e){
                    log.error("Error logging in: {}", e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String,String> error = new HashMap<>();
                    error.put("error", e.getMessage());
                    error.put("solution", "PLease login to the system again");
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
        }else{
            log.error("Invalid Refresh Token");
        }
    }
}

@Data
class RoleToUserForm{
    private String email;
    private String role;
}

@Data
class UserForm{
    private String email;
    private String password;
    private List<String> roles;
}
