package com.quikdeliver.security.oauth2;

import com.quikdeliver.advice.exception.OAuth2AuthenticationProcessingException;
import com.quikdeliver.entity.User;
import com.quikdeliver.model.AuthProvider;
import com.quikdeliver.model.GoogleAuthRequest;
import com.quikdeliver.repository.UserRepository;
import com.quikdeliver.model.UserPrincipal;
import com.quikdeliver.security.oauth2.user.OAuth2UserInfo;
import com.quikdeliver.security.oauth2.user.OAuth2UserInfoFactory;
import com.quikdeliver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service @RequiredArgsConstructor @Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final GoogleAuthRequest authRequest;
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        log.info("loadUser -DefaultOAuth2UserService");
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        log.info("processOAuth2User -DefaultOAuth2UserService");
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;

        if(authRequest.getReqType().equals("signup") && !userOptional.isPresent()) {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }else if(authRequest.getReqType().equals("signup") && userOptional.isPresent()){
            log.error("Mail already signup");
            throw new OAuth2AuthenticationProcessingException(String.format("Your email-%s already signup with this service",oAuth2UserInfo.getEmail()));
        }else if(authRequest.getReqType().equals("login") && userOptional.isPresent()) {
            user = userOptional.get();
        }else if(!authRequest.getReqType().equals("login") && !userOptional.isPresent()){
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }else{
            log.error("Invalid req arguments for google oauth request");
            throw new OAuth2AuthenticationProcessingException("Invalid req arguments for google oauth request");
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        log.info("registerNewUser -DefaultOAuth2UserService");
        User user = new User();
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        User save = userRepository.save(user);
        userService.addRoleToUser(save.getEmail(), authRequest.getUserType());
        authRequest.setUser(userRepository.findByEmail(user.getEmail()).get());// update user to global
        return save;
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        log.info("updateExistingUser -DefaultOAuth2UserService");
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}