package com.obelix.homework.platform.repo;


import com.obelix.homework.platform.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDetailsRepo extends JpaRepository<User, UUID> {
    User getUserModelByUsername(String username);

    boolean existsUserModelsByUsername(String username);
}
