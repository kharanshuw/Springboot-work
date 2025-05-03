package com.demo.security.config;

import java.io.IOException;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class OauthSuccessHandler implements AuthenticationSuccessHandler {

    public Logger logger = LoggerFactory.getLogger(OauthSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("execruting onAuthenticationSuccess method from OauthSuccessHandler class");

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

        Map<String,Object> userattributes = oAuth2User.getAttributes();

        logger.info("printing userattributes ");

        userattributes.forEach((key,value) -> {
            logger.info("{} -> {}",key,value);
        });

        new DefaultRedirectStrategy().sendRedirect(request, response, "/profile");

    }

}
