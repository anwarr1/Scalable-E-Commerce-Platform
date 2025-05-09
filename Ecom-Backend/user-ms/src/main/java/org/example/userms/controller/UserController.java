package org.example.userms.controller;

import lombok.AllArgsConstructor;
import org.example.userms.request.LoginRequest;
import org.example.userms.request.RegisterRequest;
import org.example.userms.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor

@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

        return userService.Login(loginRequest);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {

        return userService.Register(registerRequest);
    }

    @GetMapping("/getUserId")
    public Long getUserId(@RequestParam("email") String email) {

        return userService.getUserIdByUsername(email);
    }
}
