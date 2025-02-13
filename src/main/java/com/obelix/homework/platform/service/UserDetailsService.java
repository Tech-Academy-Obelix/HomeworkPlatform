package com.obelix.homework.platform.service;

import com.obelix.homework.platform.exception.UsernameExistsException;
import com.obelix.homework.platform.model.UserModel;
import com.obelix.homework.platform.repo.UserDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserDetailsRepo userDetailsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userDetailsRepo.getUserModelByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public void registerUser(UserModel user) {
        user.setRole("ROLE_STUDENT");
        if (userDetailsRepo.existsUserModelsByUsername(user.getUsername())) {
            throw new UsernameExistsException(user.getUsername());
        }
        userDetailsRepo.save(user);
    }
}
