package com.obelix.homework.platform.web.controller;

import com.obelix.homework.platform.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
        //logic for universal user logic like changing email, password, username, so on
}
