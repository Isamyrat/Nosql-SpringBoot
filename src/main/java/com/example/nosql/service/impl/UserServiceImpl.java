package com.example.nosql.service.impl;

import com.example.nosql.dto.UserDto;
import com.example.nosql.entity.Role;
import com.example.nosql.entity.User;
import com.example.nosql.exception.BadRequestException;
import com.example.nosql.repository.RoleRepository;
import com.example.nosql.repository.UserRepository;
import com.example.nosql.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUsername(username);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        if (isUsernameExist(userDto.getUsername())) {
            throw new BadRequestException("Username is occupied. Please change it");
        }
        User user = fillingInUserData(userDto);
        userRepository.save(user);
        return userDto;
    }

    private User fillingInUserData(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(getRole(userDto.getRoleName()));
        return user;
    }


    private boolean isUsernameExist(String userName) {
        return userRepository.existsByUsername(userName);
    }

    private Role getRole(String role) {
        return roleRepository.findByName(role)
            .orElseThrow(() -> new BadRequestException("Role:" + role + " not found."));
    }

    @Override
    public UserDto updateUser(UserDto userDto, String username) {
        User user = findUserByUsername(username);

        if (!user.getUsername().equals(userDto.getUsername())) {
            if (isUsernameExist(userDto.getUsername())) {
                throw new BadRequestException("Username is occupied. Please change it");
            }
        }

        User userSecond = fillingInUserData(userDto);

        userSecond.setId(user.getId());
        userRepository.save(userSecond);
        return userDto;
    }

    @Override
    public UserDto getUser(String username) {
        User user = findUserByUsername(username);
        return mapUserToDto(user);
    }

    private UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setRoleName(user.getRole().getName());
        return userDto;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();
        return userList
            .stream()
            .map(this::mapUserToDto)
            .collect(Collectors.toList());
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new BadRequestException("User:" + username + " not found."));
    }
}
