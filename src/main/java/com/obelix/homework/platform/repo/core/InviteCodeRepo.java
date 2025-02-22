package com.obelix.homework.platform.repo.core;

import com.obelix.homework.platform.model.core.entity.InviteCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InviteCodeRepo extends JpaRepository<InviteCode, UUID> {
    InviteCode getInviteCodeById(UUID id);
}
