package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

public class UserServiceImpl implements UserService {

	public UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void saveEntry(Users users) {
		userRepository.save(users);
	}

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
	
	

}
