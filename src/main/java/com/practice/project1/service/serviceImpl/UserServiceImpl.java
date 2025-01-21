package com.practice.project1.service.serviceImpl;

import com.practice.project1.model.dto.UserDto;
import com.practice.project1.model.mapping.UserMapping;
import com.practice.project1.model.dto.JwtAuthenticationDto;
import com.practice.project1.model.dto.RefreshTokenDto;
import com.practice.project1.model.dto.UserCredentialsDto;
import com.practice.project1.model.entity.User;
import com.practice.project1.repository.UserRepository;
import com.practice.project1.security.jwt.JwtService;
import com.practice.project1.service.UserService;
import jakarta.mail.AuthenticationFailedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapping userMapping;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthenticationDto authenticate(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = findUserByCredentials(userCredentialsDto);
        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if(refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findUserByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.generateRefreshToken(user.getEmail(), refreshToken);
        }
        throw new AuthenticationFailedException("Invalid refresh token");
    }

    @Override
    public void saveUser(UserDto userDto){
        User user = userMapping.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<UserDto> getUserById(Long id){
        return userRepository.findById(id).map(userMapping::toDto);
    }

    @Override
    @Transactional
    public Optional<UserDto> getUserByEmail(String email){
        return userRepository.findUserByEmail(email).map(userMapping::toDto);
    }

    @Override
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    private User findUserByCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findUserByEmail(userCredentialsDto.getEmail());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())){
                return user;
            }
        }
        throw new AuthenticationException("Credentials are incorrect");
    }

    private User findUserByEmail(String email) throws Exception {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new Exception(String.format("User with email %s not found", email)));
    }
}
