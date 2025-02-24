package com.obelix.homework.platform.web.admin.controller;

import com.obelix.homework.platform.model.user.entity.User;
import com.obelix.homework.platform.web.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserManagementController {
    private final AdminService adminService;

    @GetMapping
    public List<User> getAllUsers(){
        return adminService.getAllUsers();
    }

    @PutMapping("/{id}")
    public User updateUserRole(@PathVariable UUID id, @RequestBody String roleName) {
        return adminService.updateUserRole(id, roleName);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        adminService.deleteUser(id);
    }
}
