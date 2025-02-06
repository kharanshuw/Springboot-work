package com.example.demo.demosecurityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.service.UserdetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.passwordencoderconfig.PasswordEncoderConfig;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private UserdetailsService userdetailsService;

	private PasswordEncoderConfig passwordEncoderConfig;

	@Autowired
	public SecurityConfig(UserdetailsService userdetailsService, PasswordEncoderConfig passwordEncoderConfig) {
		super();
		this.passwordEncoderConfig = passwordEncoderConfig;
		this.userdetailsService = userdetailsService;
	}

	/**
	 * This method is used to configure the HTTP security settings for the
	 * application. It takes an HttpSecurity object as a parameter, which is used to
	 * configure the HTTP security settings.
	 * 
	 * In this method, you can configure the following:
	 * 
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
		httpSecurity.csrf(c -> c.disable()).httpBasic(Customizer.withDefaults())
				.authorizeHttpRequests(request -> request.requestMatchers("/hello").permitAll().requestMatchers("/user")
						.permitAll().anyRequest().authenticated())
				.cors(c -> c.disable());
		return httpSecurity.build();
	}

	/**
	 * This method is used to configure the global authentication settings for the
	 * application. It takes an AuthenticationManagerBuilder object as a parameter,
	 * which is used to build the authentication manager.
	 * 
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
	 * This method is used to define a bean for the PasswordEncoder interface, which
	 * is used to encode and decode passwords.
	 * 
	 * In this method, you can create a new instance of the PasswordEncoder
	 * interface and configure it to use a specific password encoding algorithm.
	 * 
	 * @return
	 */
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

}
