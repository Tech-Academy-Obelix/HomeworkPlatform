package com.obelix.homework.platform.controller;

import com.obelix.homework.platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping("/exists/{username}")
//    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable String username) {
//        return ResponseEntity.ok(userService.existsByUsername(username));
//    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        userService.changePassword(oldPassword, newPassword);
        return ResponseEntity.ok("Password changed successfully");
    }

    @PostMapping("/change-email")
    public ResponseEntity<String> changeEmail(@RequestParam String newEmail) {
        userService.changeEmail(newEmail);
        return ResponseEntity.ok("Email changed successfully");
    }
}
