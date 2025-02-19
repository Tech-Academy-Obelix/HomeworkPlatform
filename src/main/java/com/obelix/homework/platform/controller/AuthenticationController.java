package com.obelix.homework.platform.controller;

import com.obelix.homework.platform.model.dto.UserDto;
import com.obelix.homework.platform.service.UserDetailsService;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.*;

import com.obelix.homework.platform.model.entity.User;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    @PostMapping("/login")
    public String login(@RequestBody User user) throws IOException,    ServletException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), passwordEncoder.encode(user.getPassword()));
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            successHandler.onAuthenticationSuccess(null, null, authentication);
            return "redirect:/" + user.;
        } catch (AuthenticationException ex) {
            failureHandler.onAuthenticationFailure(null, null, ex);
            return "login";
        }
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
    public void register(@RequestBody UserDto user) {
        userDetailsService.registerUser(user);  // Registers the user with the provided user details
    }
}

