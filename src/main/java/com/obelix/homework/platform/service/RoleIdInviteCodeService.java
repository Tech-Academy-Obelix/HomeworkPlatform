package com.obelix.homework.platform.service;

import com.obelix.homework.platform.model.InviteCode;
import com.obelix.homework.platform.repo.InviteCodeRepo;
import com.obelix.homework.platform.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service  // Marks this class as a service component for Spring's dependency injection.
@RequiredArgsConstructor  // Generates a constructor for the final fields of this class.
public class RoleIdInviteCodeService {

    private final InviteCodeRepo inviteCodeRepo;  // Injects the InviteCode repository to interact with the database.

    // Grants an invite code for a specific role and email. Returns the UUID of the created invite code.
    public UUID grantInviteCode(Role role, String email) {
        // Saves the invite code entity with the specified role and associated email, and returns its UUID.
        return inviteCodeRepo.save(InviteCode.builder()
                .role(role.toString())  // Sets the role of the invite code (converted to String).
                .associatedEmail(email)  // Sets the associated email.
                .build()).getId();  // Returns the ID of the saved invite code.
    }

    // Retrieves an invite code by its UUID.
    public InviteCode getInviteCodeById(UUID inviteCode) {
        return inviteCodeRepo.getInviteCodeById(inviteCode);  // Fetches the invite code from the repository by its ID.
    }

    // Removes the specified invite code from the repository.
    public void removeInviteCode(InviteCode inviteCode) {
        inviteCodeRepo.delete(inviteCode);  // Deletes the given invite code from the repository.
    }
}
