package com.practice.basexbackend.services.impl;

import com.practice.basexbackend.exception.RefreshTokenException;
import com.practice.basexbackend.models.RefreshTokenX;
import com.practice.basexbackend.repository.RefreshTokenXRepo;
import com.practice.basexbackend.repository.UserXRepo;
import com.practice.basexbackend.services.RefreshTokenXService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.practice.basexbackend.constant.Security.REFRESH_TOKEN_EXP_TIME;

@Service @Transactional @RequiredArgsConstructor
public class RefreshTokenXServiceImpl implements RefreshTokenXService {

    private final RefreshTokenXRepo tokenXRepo;
    private final UserXRepo userXRepo;


    @Override
    public Optional<RefreshTokenX> getByToken(String token) {
        return tokenXRepo.findByToken(token);
    }

    @Override
    public RefreshTokenX createRefreshToken(Long id) {
        RefreshTokenX refreshTokenX = new RefreshTokenX();

        refreshTokenX.setUser(userXRepo.findById(id).get());
        refreshTokenX.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_EXP_TIME));
        refreshTokenX.setToken(UUID.randomUUID().toString());

        refreshTokenX = tokenXRepo.save(refreshTokenX);
        return refreshTokenX;
    }

    @Override
    public RefreshTokenX verifyExpiration(RefreshTokenX token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            tokenXRepo.delete(token);
            throw new RefreshTokenException(token.getToken(),
                    "Refresh token was expired. Please make a new sign in request");
        }
        return token;
    }

    @Override
    public int deleteByUserId(Long id) {
        return tokenXRepo.deleteByUser(userXRepo.findById(id).get());
    }
}
