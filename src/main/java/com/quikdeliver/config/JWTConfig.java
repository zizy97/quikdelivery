package com.quikdeliver.config;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@Getter
public class JWTConfig {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.token.prefix}")
    private String tokenPrefix;
    @Value("${jwt.accessToken.expirationAfterHours}")
    private int accessTokenValidityInHours;
    @Value("${jwt.refreshToken.expirationAfterDays}")
    private int refreshTokenValidityInDays;

    @Bean
    public byte[] getSecretKey() {
        return secretKey.getBytes();
    }

    @Bean
    public Date getAccessTokenValidityDate() {
        return new Date(System.currentTimeMillis() + this.accessTokenValidityInHours*60*60*1000);
    }

    @Bean
    public Date getRefreshTokenValidityDate() {
        return new Date(System.currentTimeMillis() + this.refreshTokenValidityInDays*24*60*60*1000);
    }
}
