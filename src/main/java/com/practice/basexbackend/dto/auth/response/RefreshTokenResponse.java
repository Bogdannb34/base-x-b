package com.practice.basexbackend.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class RefreshTokenResponse {

    private String accessToken;
    private String newRefreshToken;
}
