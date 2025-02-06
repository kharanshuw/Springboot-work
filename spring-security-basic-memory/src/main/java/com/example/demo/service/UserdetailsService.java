package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.demo.entity.Users;

import com.example.demo.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserdetailsService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public UserdetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = userRepository.findByUsername(username);

		if (users == null) {
			log.error("user with :" + username + "not found");
			new UsernameNotFoundException("user with :" + username + "not found");
		}

		String usernameString = users.getUsernameString();
		log.info("user found for username " + username);
		if (users != null) {
			UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().username(username)
					.password(users.getPasswordString()).roles(users.getRoleList().toArray(new String[0])).build();
			return userDetails;
		}

		return null;

	}

}
