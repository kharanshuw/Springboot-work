package com.example.demo.repository;

import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("SELECT u FROM Users u WHERE u.usernameString = :username")
    public Users findByUsername(@Param("username") String username);

}
