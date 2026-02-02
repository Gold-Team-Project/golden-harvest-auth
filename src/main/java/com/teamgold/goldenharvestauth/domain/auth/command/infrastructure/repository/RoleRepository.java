package com.teamgold.goldenharvestauth.domain.auth.command.infrastructure.repository;

import com.teamgold.goldenharvestauth.domain.auth.command.application.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
