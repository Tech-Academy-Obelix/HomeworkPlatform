package com.obelix.homework.platform.web.service;

import com.obelix.homework.platform.model.dto.LogDto;
import com.obelix.homework.platform.model.entity.user.User;
import com.obelix.homework.platform.repo.UserDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserDetailsRepo userDetailsRepo;

    public List<User> getAllUsers() {
        return userDetailsRepo.findAll();
    }

    public User updateUserRole() {
        return userDetailsRepo.save(updateUserRole());
    }

    public String deleteUser(Long id) {
        return userDetailsRepo.deleteById(id);
    }

    public List<LogDto> getAllLogs() {
        return null;
    }
}
