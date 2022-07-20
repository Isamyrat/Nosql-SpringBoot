package com.example.nosql.service;

import com.example.nosql.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    UserDto saveUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, String username);
    UserDto getUser(String username);
}
