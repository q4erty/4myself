package com.practice.project1.service;

import com.practice.project1.model.dto.UserDto;
import com.practice.project1.model.dto.JwtAuthenticationDto;
import com.practice.project1.model.dto.RefreshTokenDto;
import com.practice.project1.model.dto.UserCredentialsDto;

import javax.naming.AuthenticationException;
import java.util.Optional;

public interface UserService {

    JwtAuthenticationDto authenticate(UserCredentialsDto userCredentialsDto) throws AuthenticationException;

    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;

    void saveUser(UserDto userDto);

    Optional<UserDto> getUserById(Long id);

    Optional<UserDto> getUserByEmail(String email);

    void deleteUserById(Long id);

}
