package com.practice.basexbackend.services.impl;

import com.practice.basexbackend.enumeration.ERole;
import com.practice.basexbackend.models.RoleX;
import com.practice.basexbackend.models.UserX;
import com.practice.basexbackend.repository.RoleXRepo;
import com.practice.basexbackend.repository.UserXRepo;
import com.practice.basexbackend.services.RoleXService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor @Slf4j
public class RoleXServiceImpl implements RoleXService {

    private final RoleXRepo roleRepo;
    private final UserXRepo userRepo;

    @Override
    public RoleX saveRole(RoleX role) {
        log.info("Saving new role {} to the database", role.getRoleName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, ERole roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        UserX user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found with username: " + username));
        RoleX role = roleRepo.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));
        user.getRoles().add(role);
    }

}
