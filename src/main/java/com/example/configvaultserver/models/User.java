package com.example.configvaultserver.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
// import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

// @JsonInclude(JsonInclude.Include.NON_NULL) // This annotation excludes null fields from serialization
@Document(collection = "users")
@Data
public class User {

    // @JsonProperty("_id")
    private String _id;

    // @JsonProperty("name")
    private String name;

    // @JsonProperty("email")
    private String email;

    // @JsonProperty("password")
    private String password;

    // @JsonProperty("phone")
    private String phone;

    // @JsonProperty("status")
    private Integer status = 1;

    public User(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;

    }

}
