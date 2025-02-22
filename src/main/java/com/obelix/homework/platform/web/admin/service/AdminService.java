package com.obelix.homework.platform.web.admin.service;

import com.obelix.homework.platform.config.exception.UserNotFoundException;
import com.obelix.homework.platform.config.security.role.Role;
import com.obelix.homework.platform.model.user.entity.User;
import com.obelix.homework.platform.repo.user.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User updateUserRole(UUID id, String roleName) {
        var user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
        user.setRole(Role.fromString(roleName));
        return userRepo.save(user);
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepo.deleteUserById(id);
    }

    public List<String> getAllLogs() {
        return null;
    }
}
