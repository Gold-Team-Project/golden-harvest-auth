package com.teamgold.goldenharvestauth.domain.auth.command.infrastructure.repository;

import com.teamgold.goldenharvestauth.domain.auth.command.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);    //  중복체크
    Optional<User> findByEmail(String email);

    String email(String email);
}
