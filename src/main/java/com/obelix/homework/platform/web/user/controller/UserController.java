package com.obelix.homework.platform.web.user.controller;

import com.obelix.homework.platform.model.user.dto.UserDto;
import com.obelix.homework.platform.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
//    @GetMapping("/exists/{username}")
//    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable String username) {
//        return ResponseEntity.ok(userService.existsByUsername(username));
//    }

    @GetMapping
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok().body(modelMapper.map(userService.getLoggedInUser(), UserDto.class));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> payload) {
        String oldPassword = payload.get("oldPassword");
        String newPassword = payload.get("newPassword");
        userService.changePassword(oldPassword, newPassword);
        return ResponseEntity.ok("Password changed successfully");
    }

    @PostMapping("/change-email")
    public ResponseEntity<String> changeEmail(@RequestBody Map<String, String> payload) {
        userService.changeEmail(payload.get("newEmail"));
        return ResponseEntity.ok("Email changed successfully");
    }

    @PostMapping("/change-username")
    public ResponseEntity<String> changeUsername(@RequestBody Map<String, String> payload) {
        userService.changeUsername(payload.get("newUsername"));
        return ResponseEntity.ok("Username changed successfully");
    }

    @PostMapping("/change-firstname")
    public ResponseEntity<String> changeFirstName(@RequestBody Map<String, String> payload) {
        userService.changeFirstName(payload.get("newFirstName"));
        return ResponseEntity.ok("First name changed successfully");
    }

    @PostMapping("/change-lastname")
    public ResponseEntity<String> changeLastName(@RequestBody Map<String, String> payload) {
        userService.changeLastName(payload.get("newLastName"));
        return ResponseEntity.ok("Last name changed successfully");
    }
}