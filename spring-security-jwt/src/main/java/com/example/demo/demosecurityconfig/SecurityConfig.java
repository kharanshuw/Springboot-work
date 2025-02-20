package com.example.demo.demosecurityconfig;

import com.example.demo.jwtfilter.JwtFilter;
import com.example.demo.passwordencoderconfig.PasswordEncoderConfig;
import com.example.demo.service.UserdetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserdetailsServiceImpl userdetailsService;

    private PasswordEncoderConfig passwordEncoderConfig;

    private AuthenticationManager authenticationManager;

    private JwtFilter jwtFilter;


    @Autowired
    public SecurityConfig(UserdetailsServiceImpl userdetailsService, PasswordEncoderConfig passwordEncoderConfig, JwtFilter jwtFilter) {
        super();
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.userdetailsService = userdetailsService;
        this.jwtFilter = jwtFilter;
    }

    /**
     * This method is used to configure the HTTP security settings for the
     * application. It takes an HttpSecurity object as a parameter, which is used to
     * configure the HTTP security settings.
     * <p>
     * In this method, you can configure the following:
     * <p>
     * Authentication settings, such as login and logout URLs Authorization
     * settings, such as access control lists (ACLs) CSRF protection Session
     * management
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(c -> c.disable())
                .authorizeHttpRequests(request -> request.requestMatchers("/hello").hasRole("ADMIN")
                        .requestMatchers("/user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().authenticated())

                .cors(c -> c.disable())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    /**
     * This method is used to configure the global authentication settings for the
     * application. It takes an AuthenticationManagerBuilder object as a parameter,
     * which is used to build the authentication manager.
     * In this method, you can configure the authentication manager to use a
     * specific authentication provider, such as a database or an LDAP server.
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userdetailsService).passwordEncoder(passwordEncoderConfig.passwordEncoder());
    }

    /**
     * The method defines a Spring bean for AuthenticationManager by using the AuthenticationConfiguration to obtain an instance of AuthenticationManager.
     *
     * @param configuration AuthenticationConfiguration configuration: An instance of AuthenticationConfiguration that holds security configuration details.
     * @return
     * @throws Exception Throws: Exception if there is any issue in obtaining the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        /**
         * This line uses the AuthenticationConfiguration to obtain the AuthenticationManager. 
         * The AuthenticationConfiguration class is part of Spring Security and provides a way to access the AuthenticationManager bean configured in your application.
         */
        return configuration.getAuthenticationManager();
    }


}
