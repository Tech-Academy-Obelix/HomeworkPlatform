package com.obelix.homework.platform.web.user.service;

import com.obelix.homework.platform.config.exception.NoSuchRoleException;
import com.obelix.homework.platform.model.core.dto.RegisterDto;
import com.obelix.homework.platform.config.exception.UsernameExistsException;
import com.obelix.homework.platform.model.core.entity.InviteCode;
import com.obelix.homework.platform.model.user.entity.Admin;
import com.obelix.homework.platform.model.user.entity.Student;
import com.obelix.homework.platform.model.user.entity.Teacher;
import com.obelix.homework.platform.model.user.entity.User;
import com.obelix.homework.platform.repo.user.UserRepo;
import com.obelix.homework.platform.web.admin.service.InviteCodeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.obelix.homework.platform.config.security.role.Role.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final InviteCodeService inviteCodeService;
    private final PasswordEncoder passwordEncoder;

    // Loads a user by their username. Throws an exception if the user is not found.
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    // Registers a new user by using the provided user details.
    public User registerUser(RegisterDto user) {
        throwIfUsernameExists(user.getUsername());  // Checks if the username already exists, throws an exception if it does.
        InviteCode inviteCode = inviteCodeService.getInviteCodeById(user.getInviteCode());  // Fetches the invite code using the provided invite code ID.
        inviteCodeService.removeInviteCode(inviteCode);  // Removes the invite code after it’s used for registration.
        return userRepo.save(transformUserToSubclass(buildUser(inviteCode, user.getUsername())));
    }

    public Teacher getLoggedInTeacher() {
        var user = getLoggedInUser();
        if (user instanceof Teacher) {
            return (Teacher) user;
        }
        throw new ClassCastException("User is not a Teacher");
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
        return userRepo.existsUserByUsername(username);  // Returns true if the username exists, false otherwise.
    }

    // Helper method to throw an exception if a username already exists.
    private void throwIfUsernameExists(String username) {
        if (existsByUsername(username)) {  // Checks if the username exists in the database.
            throw new UsernameExistsException(username);  // Throws a custom exception if the username exists.
        }
    }

    private User transformUserToSubclass(User user) {
        return switch (user.getRole()) {
            case ROLE_ADMIN -> new Admin(user);
            case ROLE_TEACHER -> new Teacher(user);
            case ROLE_STUDENT -> new Student(user);
            case null, default -> throw new NoSuchRoleException("null");
        };
    }

    private User buildUser(InviteCode inviteCode, String username) {
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
            userRepo.save(User.builder()  // Saves a default admin user to the repository.
                    .username("admin")  // Default username for the admin.
                    .password(passwordEncoder.encode("admin"))  // Default password for the admin, encoded.
                    .role(ROLE_ADMIN)  // Sets the role to ADMIN.
                    .build());
        }
    }
}
