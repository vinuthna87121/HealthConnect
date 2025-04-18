package com.healthconnect.service;

import com.healthconnect.model.User;
import com.healthconnect.model.LoginRequest;
import com.healthconnect.repository.UserRepository;
import com.healthconnect.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private JwtTokenProvider tokenProvider;

    public User register(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Map<String, String> login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
            String token = tokenProvider.generateToken(user.getUsername());
            return Map.of("token", token);
        }
        throw new BadCredentialsException("Invalid credentials");
    }
}