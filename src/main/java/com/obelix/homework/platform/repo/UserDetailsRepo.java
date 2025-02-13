package com.obelix.homework.platform.repo;


import com.obelix.homework.platform.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserModel, Long> {
    UserDetails getUserModelByUsername(String username);

    boolean existsUserModelsByUsername(String username);
}
