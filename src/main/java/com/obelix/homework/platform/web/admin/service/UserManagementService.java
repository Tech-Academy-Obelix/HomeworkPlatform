package com.obelix.homework.platform.web.admin.service;

import com.obelix.homework.platform.config.exception.UserNotFoundException;
import com.obelix.homework.platform.config.security.role.Role;
import com.obelix.homework.platform.model.user.dto.StudentDto;
import com.obelix.homework.platform.model.user.dto.TeacherDto;
import com.obelix.homework.platform.model.user.dto.UserDto;
import com.obelix.homework.platform.model.user.entity.Student;
import com.obelix.homework.platform.model.user.entity.Teacher;
import com.obelix.homework.platform.model.user.entity.User;
import com.obelix.homework.platform.repo.user.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserManagementService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;


    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream()
                .map(this::getUserDto)
                .collect(Collectors.toList());
    }

    private UserDto getUserDto(User user) {
        return switch (user) {
            case Student student -> modelMapper.map(student, StudentDto.class);
            case Teacher teacher -> modelMapper.map(teacher, TeacherDto.class);
            case User admin -> modelMapper.map(admin, UserDto.class);
        };
    }

    public User updateUserRole(UUID id, String roleName) {
        var user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
        user.setRole(Role.fromString(roleName));
        return userRepo.save(user);
    }

    public void deleteUser(UUID id) {
        userRepo.deleteById(id);
    }
}
