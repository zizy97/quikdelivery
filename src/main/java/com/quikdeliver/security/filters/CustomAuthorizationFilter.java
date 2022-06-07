package com.quikdeliver.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.quikdeliver.model.UserModel;
import com.quikdeliver.utility.JWTHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Arrays.stream;

@Slf4j @RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter{
    private final JWTHandler jwtHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("in the doFilterInternal");
        if(request.getRequestURI().equals("/api/auth/login") || request.getRequestURI().equals("/api/auth/token/refresh")|| request.getRequestURI().equals("/api/auth/whoami") || request.getRequestURI().equals("/api/auth/signup")|| request.getRequestURI().equals("/file")){
            filterChain.doFilter(request, response);
        }else{
            log.info("JWT Verifying");
            String token = jwtHandler.getToken();
            if(token != null){
                try {
                    UserModel userModel = jwtHandler.getUserEmailAndAuthorities();
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(userModel.getEmail(), null, userModel.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                }catch (JWTVerificationException e){
                    jwtHandler.errorView(response,e);
                }
            }else{
                filterChain.doFilter(request, response);
            }
        }
    }


}
