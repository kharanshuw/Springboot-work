package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    private String usernameString;

    @Column(name = "email", nullable = false)
    private String emailString;

    @Column(name = "password", nullable = false)
    private String passwordString;

    private List<String> roleList;


}
