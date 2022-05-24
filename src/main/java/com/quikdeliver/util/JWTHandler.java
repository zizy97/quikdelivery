package com.quikdeliver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quikdeliver.config.JWTConfig;
import com.quikdeliver.entity.Role;
import com.quikdeliver.model.TokensType;
import com.quikdeliver.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component @Slf4j
public class JWTHandler {
    private final HttpServletRequest request;
    private final JWTConfig jwtConfig;
    private final Algorithm algorithm;

    public JWTHandler(HttpServletRequest request, JWTConfig jwtConfig) {
        this.request = request;
        this.jwtConfig = jwtConfig;
        this.algorithm =  Algorithm.HMAC256(jwtConfig.getSecretKey());
    }

    public DecodedJWT verifyJWTToken(String token){
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

    public String getToken() {
        String authorization = request.getHeader(AUTHORIZATION);
        if (authorization != null && authorization.startsWith(jwtConfig.getTokenPrefix())) {
            String token = authorization.substring(jwtConfig.getTokenPrefix().length());
            return token;
        }else{
            return null;
        }
    }

    public String getUserEmail(){
        String token = getToken();
        if (token != null) {
            try {
                DecodedJWT jwt = verifyJWTToken(token);
                String email = jwt.getSubject();
                return email;
            } catch (JWTVerificationException e) {
                throw new JWTVerificationException(e.getMessage());
            }
        }
        return null;
    }
    public UserModel getUserEmailAndAuthorities(){
            String token = getToken();
            if(token != null) {
                try {
                    DecodedJWT jwt = verifyJWTToken(token);
                    String email = jwt.getSubject();
                    String[] roles = jwt.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                    return new UserModel(email, authorities);
                } catch (JWTVerificationException e) {
                    throw new JWTVerificationException(e.getMessage());
                }
            }else{
                throw  new JWTVerificationException("Authorization not found or format Incorrect");
            }
    }
    public Map<String,String> buildNewToken(TokensType type, String subject, String issuer, Collection<Role> roles){
        Map<String,String > tokens = new HashMap<>();
        if(type== TokensType.ACCESS || type== TokensType.BOTH ){
            String accessToken = JWT.create()
                    .withSubject(subject)
                    .withExpiresAt(jwtConfig.getAccessTokenValidityDate())
                    .withIssuer(issuer)
                    .withClaim("roles", roles.stream().map(Role::getName).collect(Collectors.toList()))
                    .sign(algorithm);
            tokens.put("access", accessToken);
            if(type== TokensType.ACCESS)
                return tokens;
        }
        if(type== TokensType.REFRESH || type== TokensType.BOTH){
            String refreshToken = JWT.create()
                        .withSubject(subject)
                        .withExpiresAt(jwtConfig.getRefreshTokenValidityDate())
                        .withIssuer(issuer)
                        .sign(algorithm);
            tokens.put("refresh", refreshToken);
            if(type== TokensType.REFRESH)
                return tokens;
        }
        return tokens;
    }

    public void errorView(HttpServletResponse response, Exception e) throws IOException {
        log.error("Error logging in: {}", e.getMessage());
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String,String> error = new HashMap<>();
        error.put("error", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
