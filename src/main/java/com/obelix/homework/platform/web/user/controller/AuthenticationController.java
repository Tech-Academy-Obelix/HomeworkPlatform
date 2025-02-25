package com.obelix.homework.platform.web.user.controller;

import com.obelix.homework.platform.config.security.JwtUtils;
import com.obelix.homework.platform.model.core.dto.RegisterDto;
import com.obelix.homework.platform.config.security.role.Role;
import com.obelix.homework.platform.model.user.dto.UserDto;
import com.obelix.homework.platform.web.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.obelix.homework.platform.model.user.entity.User;

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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody RegisterDto user) {
        return userService.registerUser(user);  // Registers the user with the provided user details
    }
}

