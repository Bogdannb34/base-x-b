package com.practice.basexbackend.services;

import com.practice.basexbackend.models.RefreshTokenX;

import java.util.Optional;

public interface RefreshTokenXService {

    Optional<RefreshTokenX> getByToken(String token);

    RefreshTokenX createRefreshToken(Long id);

    RefreshTokenX verifyExpiration(RefreshTokenX token);

    int deleteByUserId(Long id);
}
