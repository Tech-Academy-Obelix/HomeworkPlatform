package com.obelix.homework.platform.web.controller;

import com.obelix.homework.platform.model.dto.InviteCodeDto;
import com.obelix.homework.platform.model.dto.LogDto;
import com.obelix.homework.platform.model.entity.user.User;
import com.obelix.homework.platform.web.service.AdminService;
import com.obelix.homework.platform.web.service.InviteCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController  // Marks this class as a RESTful controller (exposed to HTTP requests).
@RequestMapping("/admin")  // Defines the base URL path for this controller's endpoints.
@RequiredArgsConstructor  // Automatically generates a constructor with required fields marked as 'final'.
public class AdminController {
    private final AdminService adminService;

    private final InviteCodeService inviteCodeService;  // Injecting the RoleIdInviteCodeService to handle invite code logic.

    // Endpoint to test the controller or greet the admin.
    @GetMapping
    public String admin() {
        return "Hello Admin";  // Simple greeting for the admin.
    }

    // Endpoint to generate an invite code based on the role and email.
    @PostMapping("/invite-code")
    public String admin(@RequestBody InviteCodeDto inviteCodeDto) {
        // Calls the service to grant an invite code based on the role and email, then returns the result as a string.
        return inviteCodeService.grantInviteCode(inviteCodeDto);
    }
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return adminService.getAllUsers();
    }

    @PutMapping("/users/{id}/{role}")
    public User updateUserRole(@PathVariable UUID id, @PathVariable String roleName) {
        return adminService.updateUserRole(id, roleName);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable UUID id) {
        adminService.deleteUser(id);
    }

    @GetMapping("/logs")
    public List<LogDto> getAllLogs(){
        return adminService.getAllLogs();
    }
}