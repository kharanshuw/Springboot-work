package com.example.demo.demosecurityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.service.UserdetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private UserdetailsService userdetailsService;

	@Autowired
	public SecurityConfig(UserdetailsService userdetailsService) {
		super();
		this.userdetailsService = userdetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(c -> c.disable()).httpBasic(Customizer.withDefaults())
				.authorizeHttpRequests(
						request -> request.requestMatchers("/hello").permitAll().anyRequest().authenticated())
				.cors(c -> c.disable());
		return httpSecurity.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userdetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
