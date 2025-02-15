package com.obelix.homework.platform.repo;


import com.obelix.homework.platform.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserModel, UUID> {
    UserModel getUserModelByUsername(String username);

    boolean existsUserModelsByUsername(String username);
}
