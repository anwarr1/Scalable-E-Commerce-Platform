package org.example.userms.controller;

import lombok.AllArgsConstructor;
import org.example.userms.model.dto.AuthResponse;
import org.example.userms.model.entity.User;
import org.example.userms.repository.UserRepository;
import org.example.userms.request.LoginRequest;
import org.example.userms.request.RegisterRequest;
import org.example.userms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor

@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate and generate JWT token
            String token = userService.Login(loginRequest);

            // ✅ Load user from DB using email
            User user = userRepository.findByEmail(loginRequest.getEmail());

            // ✅ You can also convert to a UserDto to avoid exposing password or internal fields
            User userDto = User.builder().id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();

            return ResponseEntity.ok(new AuthResponse(true, "User signed in successfully", token, userDto, null));

        } catch (RuntimeException e) {
            return ResponseEntity.status(400)
                    .body(new AuthResponse(false, "Sign in failed: " + e.getMessage(), null, null, e.getMessage()));
        }
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {

        try {
            String token = userService.Register(registerRequest);
            User user = User.builder()
                    .username(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .build();
            return ResponseEntity.ok(new AuthResponse(true, "User registered successfully", token, user, null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400)
                    .body(new AuthResponse(false, "Registration failed: " + e.getMessage(), null, null, e.getMessage()));
        }

    }

    @GetMapping("/getUserId")
    public Long getUserId(@RequestParam("email") String email) {

        return userService.getUserIdByUsername(email);
    }
}
