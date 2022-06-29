package com.practice.basexbackend.constant;

public class Security {

    public static final long EXPIRATION_TIME = 7_200_000;  // 2 hrs expressed in milliseconds;
    public static final long REFRESH_TOKEN_EXP_TIME = 86_400_000;  // 24 hrs expressed in milliseconds;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String BASE_X_SPORTS_SC = "BaseX Sports, SC";
    public static final String BASE_X_ADMINISTRATION = "BaseX Sports";
    public static final String ROLES = "roles";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this info";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/api/auth/signup", "/api/auth/login", "/api/auth/token/refresh", "/api/users"};
}
