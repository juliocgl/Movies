package com.example.movies.controller;

import com.example.movies.dto.JwtRequest;
import com.example.movies.dto.JwtResponse;
import com.example.movies.service.JwtUserDetailsService;
import com.example.movies.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AuthController authController;

    @Test
    void createJwtToken_WhenValidCredentials() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("user1", "password");
        when(authenticationManager.authenticate(any()))
                .thenReturn(mock(org.springframework.security.core.Authentication.class));

        when(jwtUserDetailsService.loadUserByUsername("user1"))
                .thenReturn(mock(UserDetails.class));

        when(jwtTokenUtil.generateToken(any()))
                .thenReturn("mocked-token");

        ResponseEntity<?> responseEntity = authController.createJwtToken(jwtRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals("mocked-token", ((JwtResponse) Objects.requireNonNull(responseEntity.getBody())).getJwtToken());

        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    void createJwtToken_WhenInvalidCredentials() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("fakeUser", "password");
        when(authenticationManager.authenticate(any()))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Invalid credentials"));

        try {
            authController.createJwtToken(jwtRequest);
        } catch (Exception e) {
            assertEquals("INVALID_CREDENTIALS", e.getMessage());
        }

        verify(authenticationManager, times(1)).authenticate(any());
    }
}