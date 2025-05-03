package com.demo.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private OauthSuccessHandler oauthSuccessHandler;


    @Autowired
    public SecurityConfig(OauthSuccessHandler oauthSuccessHandler) {
        this.oauthSuccessHandler = oauthSuccessHandler;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/login").permitAll();
            auth.requestMatchers("/profile").permitAll();
            auth.anyRequest().authenticated();
        }

        );


        httpSecurity
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true).deleteCookies("JSESSIONID"));


        httpSecurity.formLogin(formlogin -> {
            formlogin.loginPage("/login");

            formlogin.loginProcessingUrl("/authenticate");

            formlogin.defaultSuccessUrl("/user/dashboard");

            // formlogin.failureForwardUrl("/login?error=true");

            formlogin.usernameParameter("email");

            formlogin.passwordParameter("password");

        });

        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/oauthlogin");


            oauth.successHandler(oauthSuccessHandler);
        });


        return httpSecurity.build();
    }
}
