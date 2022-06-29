package com.practice.basexbackend.repository;

import com.practice.basexbackend.models.UserX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserXRepo extends JpaRepository<UserX, Long> {

    Optional<UserX> findByUsername(String username);
    Optional<UserX> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
