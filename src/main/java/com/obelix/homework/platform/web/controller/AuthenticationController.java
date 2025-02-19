package com.obelix.homework.platform.web.controller;

import com.obelix.homework.platform.config.security.JwtUtils;
import com.obelix.homework.platform.model.dto.RegisterDto;
import com.obelix.homework.platform.config.security.role.Role;
import com.obelix.homework.platform.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.obelix.homework.platform.model.entity.user.User;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @GetMapping("/")
    public String home() {
        return "redirect:/" + Role.toSimpleString(userService.getLoggedInUser().getRole());
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        user = userService.loadUserByUsername(user.getUsername());
        return jwtUtils.generateToken(user.getUsername());
    }

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


    // This method will handle registration requests. The body of the request should contain user details (username, password).
    @PostMapping("/register")
    public User register(@RequestBody RegisterDto user) {
        return userService.registerUser(user);  // Registers the user with the provided user details
    }
}

