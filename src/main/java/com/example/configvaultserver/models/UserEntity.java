package com.example.configvaultserver.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false, updatable = true)
    private String name;
    @Column(name = "email", nullable = false, updatable = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, updatable = true, length = 255)
    private String password;

    @Column(name = "is_account_verified", nullable = false, updatable = true)
    private Boolean isAccountVerified;

    @Column(name = "status", nullable = false, updatable = true)
    private Boolean status;

}
