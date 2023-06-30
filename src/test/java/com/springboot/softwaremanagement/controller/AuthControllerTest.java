package com.springboot.softwaremanagement.controller;

import com.springboot.softwaremanagement.models.User;
import com.springboot.softwaremanagement.util.JwtUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtTokenUtil;

    @InjectMocks
    private AuthController authController;

    @Test
    public void givenValidCredentials_whenLogin_thenReturnToken() {
        // Given
        User user = new User("username", "password");
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("NONE")
                .build();

        String token = "jwt_token";
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(new UsernamePasswordAuthenticationToken(userDetails, null));
        given(jwtTokenUtil.generateToken(user.getUsername())).willReturn(token);

        // When
        ResponseEntity<?> response = authController.login(user);

        // Then
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(token, response.getBody());
    }

    @Test
    public void givenInvalidCredentials_whenLogin_thenReturnUnauthorized() {
        // Given
        User request = new User("username", "wrong_password");

        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willThrow(new BadCredentialsException("Invalid Credentials"));

        // When
        ResponseEntity<?> response = authController.login(request);

        // Then
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Assert.assertEquals("Invalid Credentials", response.getBody());
    }
}
