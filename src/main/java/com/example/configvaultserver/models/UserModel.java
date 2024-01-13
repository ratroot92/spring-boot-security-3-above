package com.example.configvaultserver.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.configvaultserver.dto.entity.UserDto;
import com.example.configvaultserver.enums.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
public class UserModel {

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
    private boolean isAccountVerified;

    @Column(name = "status", nullable = false, updatable = true)
    private boolean status;

    @Column(name = "role", nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public UserDto modelToDto(UserModel userEntity) {
        UserDto userDto = new UserDto();
        userDto.setAccountVerified(userEntity.isAccountVerified());
        userDto.setEmail(userEntity.getEmail());
        userDto.setId(userEntity.getId());
        userDto.setStatus(userEntity.isStatus());
        userDto.setName(userEntity.getName());
        userDto.setRole(userEntity.getRole());

        return userDto;
    }

    public UserModel dtoToModel(UserDto userDto) {
        UserModel userEntity = new UserModel();
        userEntity.setAccountVerified(userDto.isAccountVerified());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setId(userDto.getId());
        userEntity.setStatus(userDto.isStatus());
        userEntity.setName(userDto.getName());
        userEntity.setRole(userDto.getRole());

        return userEntity;
    }

}
