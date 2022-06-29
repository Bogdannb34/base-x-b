package com.practice.basexbackend.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class JWTResponse {

    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;
}
