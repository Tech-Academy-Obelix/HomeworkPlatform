package com.obelix.homework.platform.repo;


import com.obelix.homework.platform.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserDetailsRepo extends JpaRepository<User, UUID> {
    User getUserByUsername(String username);

    boolean existsUserByUsername(String username);

    User getUserById(UUID id);

    void deleteUserById(UUID id);
}
