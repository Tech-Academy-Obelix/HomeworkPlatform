package com.obelix.homework.platform.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/student")
    public String student() {
        return "Hello student!";
    }

    @GetMapping("/teacher")
    public String teacher() {
        return "Hello teacher!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello admin!";
    }
}
