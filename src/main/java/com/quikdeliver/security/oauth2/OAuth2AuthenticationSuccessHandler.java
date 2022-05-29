package com.quikdeliver.security.oauth2;

import com.quikdeliver.advice.exception.BadRequestException;
import com.quikdeliver.config.AppProperties;
import com.quikdeliver.entity.Role;
import com.quikdeliver.model.TokensType;
import com.quikdeliver.security.UserPrincipal;
import com.quikdeliver.utility.CookieUtils;
import com.quikdeliver.utility.JWTHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.quikdeliver.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component @Slf4j @RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTHandler jwtHandler;
    private final AppProperties appProperties;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("onAuthenticationSuccess-OAuth2AuthenticationSuccessHandler");
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("determineTargetUrl-OAuth2AuthenticationSuccessHandler");
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
//        String targetUrl = "http://localhost:3000/oauth2/redirect";

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<Role> roles = userPrincipal.getAuthorities().stream().map(g -> new Role(g.getAuthority())).collect(Collectors.toList());
        Map<String,String> tokens= jwtHandler.buildNewToken(TokensType.ACCESS,userPrincipal.getUsername(),request.getRequestURL().toString(),roles);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", tokens.get("access"))
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        log.info("clearAuthenticationAttributes -OAuth2AuthenticationSuccessHandler");
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        log.info("isAuthorizedRedirectUri -OAuth2AuthenticationSuccessHandler");
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}