package org.example.userms.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.userms.config.JWTService;
import org.example.userms.model.entity.User;
import org.example.userms.repository.UserRepository;
import org.example.userms.request.LoginRequest;
import org.example.userms.request.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserService {
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public String Register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()) != null)
            throw new RuntimeException("User already exists");
        User user = User.builder()
                .username(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role("USER")
                .build();
        userRepository.save(user);
        return jwtService.generateToken(registerRequest.getEmail());

    }

    public String Login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) throw new NotFoundException("User not found");
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        return jwtService.generateToken(loginRequest.getEmail());
    }

    public Long getUserIdByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new NotFoundException("User not found");

        return user.getId();
    }
}
