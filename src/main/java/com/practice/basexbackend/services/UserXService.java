package com.practice.basexbackend.services;

import com.practice.basexbackend.dto.auth.request.RegisterRequest;
import com.practice.basexbackend.models.UserX;

import java.util.List;

public interface UserXService {

    void register(RegisterRequest request);
    List<UserX> getUsers();
    UserX findByUsername(String username);
    UserX findByEmail(String email);
    Boolean checkUsernameInDB(String username);
    Boolean checkEmailInDB(String email);
    void deleteByUserXId(Long id);
}
