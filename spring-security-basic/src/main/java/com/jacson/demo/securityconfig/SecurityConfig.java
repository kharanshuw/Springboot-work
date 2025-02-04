package com.jacson.demo.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {


    /**
     * UserDetailsService: This method defines an InMemoryUserDetailsManager bean,
     * which is an implementation of UserDetailsService that stores user information in memory.
     */
    @Bean
    public UserDetailsService userDetailsService() {

        /**   The User class provides a fluent API to build UserDetails objects.

         username("pratik"): Sets the username for the user.

         password(passwordEncoder().encode("abc")): Encodes the password using the passwordEncoder bean.

         roles("ADMIN"): Assigns the role "ADMIN" to the user.

         .build(): Builds the UserDetails object */

        UserDetails userDetails = User.builder().username("pratik").password(passwordEncoder().encode("abc")).roles("ADMIN").build();
        UserDetails userDetails2 = User.builder().username("manthan").password(passwordEncoder().encode("def")).roles("USER").build();

        return new InMemoryUserDetailsManager(userDetails2, userDetails);
    }


    /**
     * PasswordEncoder: This method defines a PasswordEncoder bean that uses the BCryptPasswordEncoder implementation.
     * <p>
     * BCryptPasswordEncoder: This is a password hashing algorithm that provides strong security by hashing passwords with a random salt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
}
