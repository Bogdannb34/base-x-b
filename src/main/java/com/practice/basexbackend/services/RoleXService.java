package com.practice.basexbackend.services;

import com.practice.basexbackend.enumeration.ERole;
import com.practice.basexbackend.models.RoleX;

public interface RoleXService {

    RoleX saveRole(RoleX role);

    void addRoleToUser(String username, ERole roleName);
}
