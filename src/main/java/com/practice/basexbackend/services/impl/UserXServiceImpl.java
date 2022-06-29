package com.practice.basexbackend.services.impl;

import com.practice.basexbackend.dto.auth.request.RegisterRequest;
import com.practice.basexbackend.enumeration.ERole;
import com.practice.basexbackend.models.RoleX;
import com.practice.basexbackend.models.UserX;
import com.practice.basexbackend.repository.RoleXRepo;
import com.practice.basexbackend.repository.UserXRepo;
import com.practice.basexbackend.services.UserXService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service @Transactional @RequiredArgsConstructor @Slf4j
public class UserXServiceImpl implements UserXService {

    private final UserXRepo userXRepo;
    private final RoleXRepo roleXRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {
        UserX user = new UserX(request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()));

        Collection<String> strRoles = request.getRole();
        Collection<RoleX> roles = new HashSet<>();

        if (strRoles == null) {
            RoleX userRole = roleXRepo.findByRoleName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    RoleX adminRole = roleXRepo.findByRoleName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else if("editor".equals(role)) {
                    RoleX editorRole = roleXRepo.findByRoleName(ERole.ROLE_EDITOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(editorRole);
                } else {
                    RoleX userRole = roleXRepo.findByRoleName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userXRepo.save(user);
        log.info("User {} register successfully!", user.getUsername());
    }

    @Override
    public List<UserX> getUsers() {
        log.info("Fetching all users");
        return userXRepo.findAll();
    }

    @Override
    public UserX findByUsername(String username) {
        log.info("Fetching user: {}", username);
        return userXRepo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User with username: %s not found!", username)));
    }

    @Override
    public UserX findByEmail(String email) {
        log.info("Fetching user {}", email);
        return userXRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(String.format("User with email: %s not found!", email)));
    }

    @Override
    public Boolean checkUsernameInDB(String username) {
        return userXRepo.existsByUsername(username);
    }

    @Override
    public Boolean checkEmailInDB(String email) {
        return userXRepo.existsByEmail(email);
    }

    @Override
    public void deleteByUserXId(Long id) {
        log.info("Deleting user with id: {}", id);
        userXRepo.deleteById(id);
    }
}
