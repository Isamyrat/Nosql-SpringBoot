package com.example.nosql.service;


import com.example.nosql.dto.JwtRequestDto;
import com.example.nosql.dto.JwtResponseDto;

public interface AuthenticationService {
    JwtResponseDto login(JwtRequestDto jwtRequest);
}
