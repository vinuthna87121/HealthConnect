package com.healthconnect.model;

import javax.persistence.*;

@Entity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Getters and Setters
}