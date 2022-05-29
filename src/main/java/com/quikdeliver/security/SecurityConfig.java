package com.quikdeliver.security;

import com.quikdeliver.model.GoogleAuthRequest;
import com.quikdeliver.security.filters.CustomAuthenticationFilter;
import com.quikdeliver.security.filters.CustomAuthorizationFilter;
import com.quikdeliver.security.oauth2.CustomOAuth2UserService;
import com.quikdeliver.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.quikdeliver.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.quikdeliver.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.quikdeliver.utility.JWTHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity @RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTHandler jwtHandler;
    private  final GoogleAuthRequest authRequest;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        log.info("in the auth configure");
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        log.info("in the http configure");
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(),jwtHandler);
        customAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");//change the default login url

        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();
        http.authorizeRequests().antMatchers("/api/oauth2/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/","/api/auth/token/refresh","/api/auth/whoami","/file").permitAll();
        http.authorizeRequests().antMatchers(POST,"/api/auth/signup").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/user/**").hasAnyAuthority("ROLE_USER", "ROLE_VO");
        http.authorizeRequests().antMatchers(POST,"/api/user/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();

        http
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/api/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/api/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(jwtHandler), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository(authRequest);
    }
}
