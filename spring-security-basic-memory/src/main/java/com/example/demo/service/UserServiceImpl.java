package com.example.demo.service;

import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

//	public void saveEntry(Users users) {
//		userRepository.save(users);
//	}

	public List<Users> getallList() {
		List<Users> users = userRepository.findAll();
		return users;
	}

	public Optional<Users> getById() {
		Optional<Users> optional = userRepository.findById(1);
		return optional;
	}

	public Optional<Users> getbyUsername(String usernameString) {
		Users users = userRepository.findByUsername(usernameString);

		Optional<Users> usersOptional = Optional.of(users);

		return usersOptional;
	}

	
	public Users saveUser(Users users) {

		log.info("user to save is " + users.toString());

		String passwordString = passwordEncoder.encode(users.getPasswordString());

		log.info("encoded password is " + passwordString);

		users.setPasswordString(passwordString);

		Users users2 = userRepository.save(users);

		log.info("saved user is" + users2.toString());
		
		return users2;
	}

}
