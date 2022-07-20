package com.example.nosql.service.impl;

import com.example.nosql.dto.JwtRequestDto;
import com.example.nosql.dto.JwtResponseDto;
import com.example.nosql.entity.User;
import com.example.nosql.exception.BadRequestException;
import com.example.nosql.jwt.JwtUtils;
import com.example.nosql.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final String USER_NAME_NOT_BE_NULL = "Username should not be null or blank.";
    public static final String PASSWORD_NOT_BE_NULL = "Password should not be null or blank.";
    public static final String USER_NOT_FOUND = "User not found.";

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public JwtResponseDto login(JwtRequestDto jwtRequest) {
        validateLoginRequest(jwtRequest);
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadRequestException(USER_NOT_FOUND, e);
        }
        return new JwtResponseDto(jwtUtils.getJwtToken(jwtRequest.getUsername()));
    }

    private void validateLoginRequest(JwtRequestDto jwtRequest) {
        if (jwtRequest.getUsername() == null || jwtRequest.getUsername().isBlank()) {
            throw new BadRequestException(USER_NAME_NOT_BE_NULL);
        }
        if (jwtRequest.getPassword() == null || jwtRequest.getPassword().isBlank()) {
            throw new BadRequestException(PASSWORD_NOT_BE_NULL);
        }
    }
}
