package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserdetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserdetailsServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    /**
     * This method is used to load a user by their username. It takes a String
     * parameter username and returns a UserDetails object.
     *
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username);

        if (users != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(users.getUsernameString())
                    .password(users.getPasswordString())
                    .roles(users.getRoleList().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("username not found with username : " + username);
    }

}
