package com.obelix.homework.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Hello World";
    }

    @GetMapping("/teacher")
    public String teacher() {
        return "Hello Teacher";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin";
    }
}
