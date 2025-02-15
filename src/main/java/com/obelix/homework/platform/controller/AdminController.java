package com.obelix.homework.platform.controller;

import com.obelix.homework.platform.role.Role;
import com.obelix.homework.platform.service.RoleIdInviteCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController  // Marks this class as a RESTful controller (exposed to HTTP requests).
@RequestMapping("/admin")  // Defines the base URL path for this controller's endpoints.
@RequiredArgsConstructor  // Automatically generates a constructor with required fields marked as 'final'.
public class AdminController {

    private final RoleIdInviteCodeService inviteCodeService;  // Injecting the RoleIdInviteCodeService to handle invite code logic.

    // Endpoint to test the controller or greet the admin.
    @GetMapping
    public String admin() {
        return "Hello Admin";  // Simple greeting for the admin.
    }

    // Endpoint to generate an invite code based on the role and email.
    @GetMapping("invite-code/{role}/{email}")
    public String admin(@PathVariable String role, @PathVariable String email) {
        // Calls the service to grant an invite code based on the role and email, then returns the result as a string.
        return inviteCodeService.grantInviteCode(Role.fromString(role), email).toString();
    }
}