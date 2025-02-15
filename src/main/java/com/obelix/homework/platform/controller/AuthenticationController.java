package com.obelix.homework.platform.controller;

import com.obelix.homework.platform.Dto.UserDto;
import com.obelix.homework.platform.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.obelix.homework.platform.model.UserModel;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // This method should return the HTML code for the login form
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // This method should return the HTML code for the registration form
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // This method will handle login requests. The body of the request should contain the user's credentials (username, password).
    @PostMapping("/login")
    public void login(@RequestBody UserModel userModel) {
        userDetailsService.loadUserByUsername(userModel.getUsername());  // Loads user details by username
    }

    // This method will handle registration requests. The body of the request should contain user details (username, password).
    @PostMapping("/register")
    public void register(@RequestBody UserDto user) {
        userDetailsService.registerUser(user);  // Registers the user with the provided user details
    }
}
