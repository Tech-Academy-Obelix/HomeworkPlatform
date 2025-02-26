package com.obelix.homework.platform.web.user.service;

import com.obelix.homework.platform.config.exception.IncorrectPasswordException;
import com.obelix.homework.platform.config.exception.NoSuchRoleException;
import com.obelix.homework.platform.model.core.dto.RegisterDto;
import com.obelix.homework.platform.config.exception.UsernameExistsException;
import com.obelix.homework.platform.model.core.entity.InviteCode;
import com.obelix.homework.platform.model.user.dto.UserDto;
import com.obelix.homework.platform.model.user.entity.Admin;
import com.obelix.homework.platform.model.user.entity.Student;
import com.obelix.homework.platform.model.user.entity.Teacher;
import com.obelix.homework.platform.model.user.entity.User;
import com.obelix.homework.platform.repo.user.UserRepo;
import com.obelix.homework.platform.web.admin.service.InviteCodeService;
import com.obelix.homework.platform.config.logging.LogService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final LogService logService;
    private final ModelMapper modelMapper;

    // Loads a user by their username. Throws an exception if the user is not found.
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    // Registers a new registerDto by using the provided registerDto details.
    public UserDto registerUser(RegisterDto registerDto) {
        verifyUsernameAvailable(registerDto.getUsername());  // Checks if the username already exists, throws an exception if it does.
        var inviteCode = inviteCodeService.getInviteCodeById(registerDto.getInviteCode());  // Fetches the invite code using the provided invite code ID.
        inviteCodeService.deleteInviteCodeById(inviteCode.getId());  // Removes the invite code after it’s used for registration.
        return modelMapper.map(userRepo.save(transformUserToSubclass(buildUser(inviteCode, registerDto))), UserDto.class);
    }

    // Checks if a username already exists in the repository.
    public boolean existsByUsername(String username) {
        return userRepo.existsUserByUsername(username);  // Returns true if the username exists, false otherwise.
    }

    public void changePassword(String oldPassword, String newPassword) {
        var user = getLoggedInUser();
        verifyPassword(oldPassword, user);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        logService.info(String.format("User with id: %s changed password.", user.getId()));
    }

    public void changeEmail(String newEmail) {
        var user = getLoggedInUser();
        user.setEmail(newEmail);
        userRepo.save(user);
        logService.info(String.format("User with id: %s changed email to %s.", user.getId(), user.getEmail()));
    }

    public void changeUsername(String newUsername) {
        verifyUsernameAvailable(newUsername);
        var user = getLoggedInUser();
        user.setUsername(newUsername);
        userRepo.save(user);
        logService.info(String.format("User with id: %s changed username to %s.", user.getId(), user.getUsername()));
    }

    public void changeFirstName(String newFirstName) {
        var user = getLoggedInUser();
        user.setFirstName(newFirstName);
        userRepo.save(user);
        logService.info(String.format("User with id: %s changed first name to %s.", user.getId(), user.getFirstName()));
    }

    public void changeLastName(String newLastName) {
        var user = getLoggedInUser();
        user.setLastName(newLastName);
        userRepo.save(user);
        logService.info(String.format("User with id: %s changed last name to %s.", user.getId(), user.getLastName()));
    }

    // Helper method to throw an exception if a username already exists.
    private void verifyUsernameAvailable(String username) {
        if (existsByUsername(username)) {  // Checks if the username exists in the database.
            throw new UsernameExistsException(username);  // Throws a custom exception if the username exists.
        }
    }

    private void verifyPassword(String oldPassword, User user) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            logService.warn(String.format("User with id: %s entered wrong password.", user.getId()));
            throw new IncorrectPasswordException(user.getUsername());
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

    private User buildUser(InviteCode inviteCode, RegisterDto registerDto) {
        return User.builder()
                .username(registerDto.getUsername())
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .password(passwordEncoder.encode(inviteCode.toString())) // Password encoded from the invite code
                .email(inviteCode.getAssociatedEmail())
                .role(inviteCode.getRole())  // Set the role from the invite code
                .build();
    }

    public Student getLoggedInStudent() {
        var user = getLoggedInUser();
        if (user instanceof Student) {
            return (Student) user;
        }
        throw new ClassCastException("User not authenticated");
    }

    public Teacher getLoggedInTeacher() {
        var user = getLoggedInUser();
        if (user instanceof Teacher) {
            return (Teacher) user;
        }
        throw new ClassCastException("User is not a Teacher");
    }

    public User getLoggedInUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            var principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal; // Cast to your User class
            }
        }
        return null; // If no authentication is present
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