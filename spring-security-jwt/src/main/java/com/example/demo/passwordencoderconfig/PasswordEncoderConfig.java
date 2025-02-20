package com.example.demo.passwordencoderconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    /**
     * This method is used to define a bean for the PasswordEncoder interface, which
     * is used to encode and decode passwords.
     * <p>
     * In this method, you can create a new instance of the PasswordEncoder
     * interface and configure it to use a specific password encoding algorithm.
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
