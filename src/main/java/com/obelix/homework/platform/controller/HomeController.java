package com.obelix.homework.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/student")
    public String home() {
        return "Hello Student";
    }

    @GetMapping("/teacher")
    public String teacher() {
        return "Hello Teacher";
    }
}
