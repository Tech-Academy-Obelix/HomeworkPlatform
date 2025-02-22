package com.obelix.homework.platform.repo.user;


import com.obelix.homework.platform.model.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    User getUserByUsername(String username);

    boolean existsUserByUsername(String username);

    void deleteUserById(UUID id);
}
