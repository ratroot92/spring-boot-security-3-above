package com.example.configvaultserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import com.example.configvaultserver.services.RecaptchaService;

@Configuration
@EnableWebSecurity
// @EnableMethodSecurity
public class SecurityConfig {

    public SecurityConfig(RecaptchaService recaptchaService, CustomUserDetailService customUserDetailService) {

    }

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                // .csrf(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        (authorize) -> {
                            authorize.requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll();
                            authorize.anyRequest().authenticated();
                        })
                // Setup OAuth2 with JWT
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                // Turn Of Session Management (make then stateless) # Rest Apis
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(User.withUsername("admin").password("admin").authorities("read").build());
    }

    // public AuthenticationManager authenticationManager(UserDetailsService
    // userDetailsService) {
    // var authenticationProvider = new DaoAuthenticationProvider();
    // authenticationProvider.setUserDetailsService(userDetailsService);
    // return new ProviderManager(authenticationProvider);
    // }

}