package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Users;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {

	private UserService userService;

	@Autowired
	public TestController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/hello")
	public String getname() {
		return "hello";
	}

	/**
	 * 
	 * @param users
	 * @return
	 */
	@PostMapping("/user")
	public String createUser(@RequestBody @Validated Users users) {
		log.info("user recived from requestbody is " + users.toString());
		Users users2 = userService.saveUser(users);
		return users2.toString();
	}

}
