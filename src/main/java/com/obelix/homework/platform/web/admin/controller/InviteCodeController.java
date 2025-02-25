package com.obelix.homework.platform.web.admin.controller;

import com.obelix.homework.platform.model.core.dto.InviteCodeDto;
import com.obelix.homework.platform.model.core.entity.InviteCode;
import com.obelix.homework.platform.web.admin.service.InviteCodeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/invite-codes")
public class InviteCodeController {
    private final InviteCodeService inviteCodeService;

    @GetMapping()
    public List<InviteCode> getInviteCodes() {
        return inviteCodeService.getAllInviteCodes();
    }

    @PostMapping
    public String grantInviteCode(@RequestBody InviteCodeDto inviteCodeDto) {
        // Calls the service to grant an invite code based on the role and email, then returns the result as a string.
        return inviteCodeService.grantInviteCode(inviteCodeDto);
    }

    @DeleteMapping("/{id}")
    public void revokeInviteCode(@PathVariable UUID id) {
        inviteCodeService.deleteInviteCodeById(id);
    }
}
