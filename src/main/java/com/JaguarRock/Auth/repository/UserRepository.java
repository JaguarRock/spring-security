package com.JaguarRock.Auth.repository;

import com.JaguarRock.Auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUid(String email);

    Optional<User> findByUidAndProvider(String id, String provider);
}
