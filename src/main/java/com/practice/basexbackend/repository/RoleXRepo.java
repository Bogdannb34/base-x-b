package com.practice.basexbackend.repository;

import com.practice.basexbackend.enumeration.ERole;
import com.practice.basexbackend.models.RoleX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleXRepo extends JpaRepository<RoleX, Long> {
    Optional<RoleX> findByRoleName(ERole roleName);
}
