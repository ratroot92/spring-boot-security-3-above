package com.example.configvaultserver.security;

import org.slf4j.Logger;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.configvaultserver.models.UserEntity;
import com.example.configvaultserver.repository.UserRepository;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity appUser = userRepository.findByEmail(email);
        if (appUser == null) {
            throw new UsernameNotFoundException(email);
        }

        boolean enabled = !appUser.getIsAccountVerified();
        UserDetails user = User.withUsername(appUser.getEmail())
                .password(appUser.getPassword())
                .disabled(enabled)
                .authorities("USER").build();

        return user;

    }
}
