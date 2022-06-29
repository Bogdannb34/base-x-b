package com.practice.basexbackend.resource.authController;

import com.practice.basexbackend.dto.auth.request.LogOutRequest;
import com.practice.basexbackend.dto.auth.request.LoginRequest;
import com.practice.basexbackend.dto.auth.request.RefreshTokenRequest;
import com.practice.basexbackend.dto.auth.request.RegisterRequest;
import com.practice.basexbackend.dto.auth.response.JWTResponse;
import com.practice.basexbackend.dto.auth.response.MessageResponse;
import com.practice.basexbackend.dto.auth.response.RefreshTokenResponse;
import com.practice.basexbackend.exception.RefreshTokenException;
import com.practice.basexbackend.models.RefreshTokenX;
import com.practice.basexbackend.models.UserX;
import com.practice.basexbackend.security.jwt.JWTTokenProvider;
import com.practice.basexbackend.services.RefreshTokenXService;
import com.practice.basexbackend.services.UserXService;
import com.practice.basexbackend.services.impl.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor @Slf4j
public class UserXResource {

    private final UserXService userXService;
    private final RefreshTokenXService tokenXService;
    private final AuthenticationManager authManager;
    private final JWTTokenProvider jwtTokenProvider;

    @GetMapping("/users")
    public ResponseEntity<List<UserX>> getUsers() {
        return ResponseEntity.ok().body(userXService.getUsers());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        authenticate(request);
        UserX user = userXService.findByUsername(request.getUsername());
        CustomUserDetails userDetails = CustomUserDetails.build(user);
        String jwt = jwtTokenProvider.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(
                GrantedAuthority::getAuthority).toList();
        RefreshTokenX refreshTokenX = tokenXService.createRefreshToken(userDetails.getId());
        return ResponseEntity.ok(new JWTResponse(userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail(), roles, jwt, refreshTokenX.getToken()));
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        if(userXService.checkUsernameInDB(request.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userXService.checkEmailInDB(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        userXService.register(request);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/auth/token/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        String requestRefToken = request.getRefreshToken();

        return tokenXService.getByToken(requestRefToken)
                .map(tokenXService::verifyExpiration)
                .map(RefreshTokenX::getUser)
                .map(user -> {
                    CustomUserDetails userDetails = CustomUserDetails.build(user);
                    String newToken = jwtTokenProvider.generateJwtToken(userDetails);
                    log.info("New refreshToken created successfully!");
                    return ResponseEntity.ok(new RefreshTokenResponse(newToken, requestRefToken));
                })
                .orElseThrow(() -> new RefreshTokenException(requestRefToken, "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest request) {
        tokenXService.deleteByUserId(request.getId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }


    private void authenticate(LoginRequest loginRequest) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));
    }
}
