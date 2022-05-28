package com.quikdeliver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
public class AppProperties {

    private final OAuth2 oauth2 = new OAuth2();

    @Component
    public static final class OAuth2 {
        @Value("#{'${app.oauth2.authorizedRedirectUris}'.split(',')}")
        private List<String> authorizedRedirectUris;

        @Bean
        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

    @Bean
    public OAuth2 getOauth2() {
        return oauth2;
    }
}