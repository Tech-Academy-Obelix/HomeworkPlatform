package com.obelix.homework.platform.web.admin.controller;

import com.obelix.homework.platform.model.user.dto.UserDto;
import com.obelix.homework.platform.model.user.entity.User;
import com.obelix.homework.platform.web.admin.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserManagementController {
    private final UserManagementService userManagementService;

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userManagementService.getAllUsers();
    }

    @PutMapping("/{id}")
    public User updateUserRole(@PathVariable UUID id, @RequestBody String roleName) {
        return userManagementService.updateUserRole(id, roleName);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userManagementService.deleteUser(id);
    }
}
