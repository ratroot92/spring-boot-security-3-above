package com.example.configvaultserver.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "users")
@Data
public class UserEntity {
    private String _id;
    private String email;
    private String password;
    private Boolean isAccountVerified;
    private String name;
    private Boolean status;

}
