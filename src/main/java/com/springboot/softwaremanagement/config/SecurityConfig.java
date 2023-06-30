package com.springboot.softwaremanagement.config;

import com.springboot.softwaremanagement.models.User;
import com.springboot.softwaremanagement.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;


@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

//    @Autowired
//    public SecurityConfig(UserAuthenticationService userAuthenticationService, JwtRequestFilter jwtRequestFilter) {
//        this.userAuthenticationService = userAuthenticationService;
//        this.jwtRequestFilter = jwtRequestFilter;
//    }

    public void configure(HttpSecurity http) throws Exception {
        http.csrf(csrf ->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authentication -> {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            // Authenticate user using UserAuthenticationService
            User authenticatedUser = userAuthenticationService.authenticate(username, password);

            if (authenticatedUser != null) {
                return new UsernamePasswordAuthenticationToken(username, password);
            } else {
                throw new BadCredentialsException("Invalid Credentials");
            }
        };
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // Use a proper password encoder for production
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userAuthenticationService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }

//    @Bean
//    public AuthenticationManager authenticationManager() {
//        return new ProviderManager(Collections.singletonList(authenticationProvider()));
//    }


}

