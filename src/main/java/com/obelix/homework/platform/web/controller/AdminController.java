package com.obelix.homework.platform.web.controller;

import com.obelix.homework.platform.model.dto.InviteCodeDto;
import com.obelix.homework.platform.config.security.role.Role;
import com.obelix.homework.platform.web.service.InviteCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController  // Marks this class as a RESTful controller (exposed to HTTP requests).
@RequestMapping("/admin")  // Defines the base URL path for this controller's endpoints.
@RequiredArgsConstructor  // Automatically generates a constructor with required fields marked as 'final'.
public class AdminController {

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
}