package com.obelix.homework.platform.controller;

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

    //Този метод трябва да връща HTML кода за логин формата
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //Този метод трябва да връща HTML кода за логин формата
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /*
        В тялото на request-ите за тези два метода трябва да се съдържа информация за потребитяла
        {
            "username":"usr",
            "password":"pswd"
         }
     */
    @PostMapping("/login")
    public void login(@RequestBody UserModel userModel) {
        userDetailsService.loadUserByUsername(userModel.getUsername());
    }

    @PostMapping("/register")
    public void register(@RequestBody UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetailsService.registerUser(user);
    }
}
