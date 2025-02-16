package com.obelix.homework.platform.service;

import com.obelix.homework.platform.Dto.UserDto;
import com.obelix.homework.platform.exception.UsernameExistsException;
import com.obelix.homework.platform.model.InviteCode;
import com.obelix.homework.platform.model.UserModel;
import com.obelix.homework.platform.repo.UserDetailsRepo;
import com.obelix.homework.platform.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // Marks this class as a service component for Spring, so it can be injected into other components.
@RequiredArgsConstructor  // Automatically generates a constructor for final fields (dependency injection).
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserDetailsRepo userDetailsRepo;  // Injects the repository for user details to interact with the database.
    private final InviteCodeService inviteCodeService;  // Injects the service for handling invite codes.
    private final PasswordEncoder passwordEncoder;  // Injects the password encoder to encode passwords.

    // Loads a user by their username. Throws an exception if the user is not found.
    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userDetailsRepo.getUserModelByUsername(username);  // Fetches the user from the repository by username.
        if (user == null) {  // If no user is found, throws a UsernameNotFoundException.
            throw new UsernameNotFoundException(username);  // Username is not found.
        }
        return user;  // Returns the found user.
    }

    // Registers a new user by using the provided user details.
    public void registerUser(UserDto user) {
        throwIfUsernameExists(user.getUsername());  // Checks if the username already exists, throws an exception if it does.
        InviteCode inviteCode = inviteCodeService.getInviteCodeById(user.getInviteCode());  // Fetches the invite code using the provided invite code ID.
        userDetailsRepo.save(buildUser(inviteCode, user.getUsername()));
        inviteCodeService.removeInviteCode(inviteCode);  // Removes the invite code after it’s used for registration.
    }

    // Checks if a username already exists in the repository.
    public boolean existsByUsername(String username) {
        return userDetailsRepo.existsUserModelsByUsername(username);  // Returns true if the username exists, false otherwise.
    }

    // Creates a default admin user if it does not already exist.
    public void createDefaultAdmin() {
        throwIfUsernameExists("admin");  // Checks if the 'admin' username exists. If it does, an exception is thrown.
        userDetailsRepo.save(UserModel.builder()  // Saves a default admin user to the repository.
                .username("admin")  // Default username for the admin.
                .password(passwordEncoder.encode("admin"))  // Default password for the admin, encoded.
                .role(Role.ADMIN.toString())  // Sets the role to ADMIN.
                .build());
    }

    // Helper method to throw an exception if a username already exists.
    private void throwIfUsernameExists(String username) {
        if (existsByUsername(username)) {  // Checks if the username exists in the database.
            throw new UsernameExistsException(username);  // Throws a custom exception if the username exists.
        }
    }

    private UserModel buildUser(InviteCode inviteCode, String username) {
        return UserModel.builder()  // Saves a new user in the repository.
                .username(username)  // Sets the username for the new user.
                .password(passwordEncoder.encode(inviteCode.toString()))  // Encodes the invite code as the password (this is likely not the intended behavior, as passwords should be different).
                .email(inviteCode.getAssociatedEmail())  // Sets the user's email based on the invite code.
                .role(inviteCode.getRole())  // Sets the user's role based on the invite code.
                .build();
    }
}
