package com.obelix.homework.platform.web.controller;

import com.obelix.homework.platform.model.dto.AdminDto;
import com.obelix.homework.platform.model.dto.InviteCodeDto;
import com.obelix.homework.platform.config.security.role.Role;
import com.obelix.homework.platform.model.dto.LogDto;
import com.obelix.homework.platform.model.entity.user.User;
import com.obelix.homework.platform.web.service.AdminService;
import com.obelix.homework.platform.web.service.InviteCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return inviteCodeService.grantInviteCode(
                Role.fromString(inviteCodeDto.getRoleName()),
                inviteCodeDto.getEmailAssociated()
        );
    }
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return adminService.getAllUsers();
    }

    @PostMapping("/users/{id}/roles")
    public User updateUserRole(@PathVariable String user, @RequestBody AdminDto adminDto) {
        return adminService.updateUserRole();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }

    @GetMapping("/logs")
    public List<LogDto> getAllLogs(){
        return adminService.getAllLogs();
    }



}