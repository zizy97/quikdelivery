package com.quikdeliver.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quikdeliver.entity.Role;
import com.quikdeliver.model.TokensType;
import com.quikdeliver.util.JWTHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j @RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTHandler jwtHandler;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("in the attemptAuthentication");
        if(request.getParameter("email") != null && request.getParameter("password") != null) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            log.info("Attempting authentication for email: {}", email);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
            return authenticationManager.authenticate(authToken);
        }else{
            throw new AuthenticationException("Authentication failed") {};
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        jwtHandler.errorView(response,failed);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        log.info("in the successfulAuthentication");
        User user = (User) authentication.getPrincipal();
        List<Role> roles = user.getAuthorities().stream().map(g -> new Role(g.getAuthority())).collect(Collectors.toList());

        Map<String,String> tokens =
                jwtHandler.buildNewToken(
                        TokensType.BOTH,user.getUsername(),
                        request.getRequestURL().toString(),
                        roles
                );
        Map<String,Object> out = new HashMap<>();//response object
        String[] userRoles=user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
        out.put("userId",user.getUsername());
        out.put("access",new String(tokens.get("access")));
        out.put("refresh",new String(tokens.get("refresh")));
        out.put("roles",userRoles);
        response.setContentType(APPLICATION_JSON_VALUE); // Content type
        new ObjectMapper().writeValue(response.getOutputStream(),out);
    }
}
