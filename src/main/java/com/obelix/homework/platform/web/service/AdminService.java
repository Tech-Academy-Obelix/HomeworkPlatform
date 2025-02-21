package com.obelix.homework.platform.web.service;

import com.obelix.homework.platform.config.security.role.Role;
import com.obelix.homework.platform.model.dto.LogDto;
import com.obelix.homework.platform.model.entity.user.User;
import com.obelix.homework.platform.repo.UserDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserDetailsRepo userDetailsRepo;

    public List<User> getAllUsers() {
        return userDetailsRepo.findAll();
    }

    public User updateUserRole(UUID id, String roleName) {
        var user = userDetailsRepo.getUserById(id);
        user.setRole(Role.fromString(roleName));
        return userDetailsRepo.save(user);
    }

    public void deleteUser(UUID id) {
        userDetailsRepo.deleteUserById(id);
    }

    public List<LogDto> getAllLogs() {
        return null;
    }
}
