package com.example.demo.controller;

import com.example.demo.entity.*;
import  com.example.demo.service.*;
import com.example.demo.jwtutils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class TestController {

	private UserService userService;

	private AuthenticationManager authenticationManager;

	private UserdetailsServiceImpl userdetailsServiceImpl;

	private JwtUtils jwtUtils;

	@Autowired
	public TestController(UserService userService, AuthenticationManager authenticationManager,
			UserdetailsServiceImpl userdetailsServiceImpl, JwtUtils jwtUtils) {
		super();
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.userdetailsServiceImpl = userdetailsServiceImpl;
		this.jwtUtils = jwtUtils;

	}

	@GetMapping("/hello")
	public String getname() {
		return "hello";
	}

	/**
	 * this method is used to create new user
	 * @param users
	 * @return
	 */
	@PostMapping("/signup")
	public String createUser(@RequestBody @Validated Users users) {
		log.info("user recived from requestbody is " + users.toString());
		Users users2 = userService.saveUser(users);
		return users2.toString();
	}

	/**
	 * The loginUser method is a POST endpoint that handles user login. 
	 * It authenticates the user, generates a JWT token upon successful authentication, and returns the token to the client.
	 * @param users
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody Users users) {
		log.info("login method executed");

		try {
			/**
			 * Purpose: Tries to authenticate the user using the provided username and
			 * password. Method: authenticationManager.authenticate(...) attempts to
			 * authenticate the user with the provided credentials. If authentication fails,
			 * it throws an exception.
			 */
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(users.getUsernameString(), users.getPasswordString()));

			/**
			 * Purpose: Loads the user details using the username. Method:
			 * userdetailsServiceImpl.loadUserByUsername(...) retrieves user details from a
			 * custom UserDetailsService implementation.
			 */
			UserDetails userDetails = userdetailsServiceImpl.loadUserByUsername(users.getUsernameString());

			/**
			 * Purpose: Generates a JWT token for the authenticated user. Method:
			 * jwtUtils.generateToken(...) creates a JWT token using the username. Return:
			 * Returns the generated JWT token with an HTTP status of OK.
			 */
			String jwtToken = jwtUtils.generateToken(userDetails.getUsername());
			
			log.info("generated jwt token is " + jwtToken.toString());
			
			
			return new ResponseEntity<>(jwtToken, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred while createAuthenticationToken ", e);
			return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
		}

	}

}
