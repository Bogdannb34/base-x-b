package com.practice.basexbackend.repository;

import com.practice.basexbackend.models.RefreshTokenX;
import com.practice.basexbackend.models.UserX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenXRepo extends JpaRepository<RefreshTokenX, Long> {

    Optional<RefreshTokenX> findByToken(String token);

    @Modifying
    int deleteByUser(UserX user);
}
