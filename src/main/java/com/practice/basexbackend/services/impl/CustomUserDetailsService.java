package com.practice.basexbackend.services.impl;

import com.practice.basexbackend.models.UserX;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional @Slf4j @RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserXServiceImpl userXService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserX user = userXService.findByUsername(username);
        return CustomUserDetails.build(user);
    }
}
