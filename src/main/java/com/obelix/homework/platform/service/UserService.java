package com.obelix.homework.platform.service;

import com.obelix.homework.platform.model.dto.RegisterDto;
import com.obelix.homework.platform.exception.UsernameExistsException;
import com.obelix.homework.platform.model.entity.core.InviteCode;
import com.obelix.homework.platform.model.entity.users.User;
import com.obelix.homework.platform.repo.UserDetailsRepo;
import com.obelix.homework.platform.role.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDetailsRepo userDetailsRepo;
    private final InviteCodeService inviteCodeService;
    private final PasswordEncoder passwordEncoder;

    // Loads a user by their username. Throws an exception if the user is not found.
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userDetailsRepo.getUserModelByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

/*    public User findUserByUsername(String username) throws UsernameNotFoundException {
        var user = userDetailsRepo.getUserModelByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public User authenticate(CredentialsDto credentialsDto) {
        User user = userDetailsRepo.getUserModelByUsername(credentialsDto.getUsername());
        if (passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
            return user;
        }
        throw new RuntimeException("Invalid password");
    }

 */

    // Registers a new user by using the provided user details.
    public User registerUser(RegisterDto user) {
        throwIfUsernameExists(user.getUsername());  // Checks if the username already exists, throws an exception if it does.
        InviteCode inviteCode = inviteCodeService.getInviteCodeById(user.getInviteCode());  // Fetches the invite code using the provided invite code ID.
        inviteCodeService.removeInviteCode(inviteCode);  // Removes the invite code after it’s used for registration.
        return userDetailsRepo.save(buildUser(inviteCode, user.getUsername()));
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal; // Cast to your User class
            }
        }

        return null; // If no authentication is present
    }

    // Checks if a username already exists in the repository.
    public boolean existsByUsername(String username) {
        return userDetailsRepo.existsUserModelsByUsername(username);  // Returns true if the username exists, false otherwise.
    }

    // Helper method to throw an exception if a username already exists.
    private void throwIfUsernameExists(String username) {
        if (existsByUsername(username)) {  // Checks if the username exists in the database.
            throw new UsernameExistsException(username);  // Throws a custom exception if the username exists.
        }
    }

    private User buildUser(InviteCode inviteCode, String username) {
        // Create the common user data (e.g., username, password, email)
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(inviteCode.toString())) // Password encoded from the invite code
                .email(inviteCode.getAssociatedEmail())
                .role(inviteCode.getRole())  // Set the role from the invite code
                .build();
    }

    @PostConstruct
    protected void init() {
        if (!existsByUsername("admin")) {
            userDetailsRepo.save(User.builder()  // Saves a default admin user to the repository.
                    .username("admin")  // Default username for the admin.
                    .password(passwordEncoder.encode("admin"))  // Default password for the admin, encoded.
                    .role(Role.ROLE_ADMIN)  // Sets the role to ADMIN.
                    .build());
        }
    }

    public void changePassword(String oldPassword, String newPassword) {
        User user = getLoggedInUser();
        if (user == null) {
            throw new RuntimeException("User not authenticated");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userDetailsRepo.save(user);
    }

    public void changeEmail(String newEmail) {
        User user = getLoggedInUser();
        if (user == null) {
            throw new RuntimeException("User not authenticated");
        }

        user.setEmail(newEmail);
        userDetailsRepo.save(user);
    }

}
