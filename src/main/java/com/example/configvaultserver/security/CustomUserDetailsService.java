package com.example.configvaultserver.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.configvaultserver.models.UserModel;
import com.example.configvaultserver.repository.UserRepository;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserModel appUser = userRepository.findByEmail(email);
        if (appUser == null) {
            throw new UsernameNotFoundException(email);
        }
        boolean enabled = !appUser.isAccountVerified();
        UserDetails user = User.withUsername(appUser.getEmail())
                .password(appUser.getPassword())
                .disabled(enabled)
                .authorities(appUser.getAuthorities()).build();
        return user;

    }
}
