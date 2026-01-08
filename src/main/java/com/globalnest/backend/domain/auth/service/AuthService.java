package com.globalnest.backend.domain.auth.service;

import com.globalnest.backend.api.auth.dto.request.LoginRequest;
import com.globalnest.backend.api.auth.dto.request.RefreshTokenRequest;
import com.globalnest.backend.api.auth.dto.response.LoginResponse;
import com.globalnest.backend.api.auth.dto.response.TokenResponse;
import com.globalnest.backend.domain.user.entity.User;
import com.globalnest.backend.domain.user.repository.UserRepository;
import com.globalnest.backend.global.exception.UnauthorizedException;
import com.globalnest.backend.global.security.CustomUserDetails;
import com.globalnest.backend.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest request) {
        // Authenticate
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Generate tokens
        String accessToken = jwtUtil.generateAccessToken(userDetails.getEmail(), userDetails.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getEmail());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userDetails.getUserId())
                .email(userDetails.getEmail())
                .role(userDetails.getRole())
                .agentId(userDetails.getAgentId())
                .build();
    }

    public TokenResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        String email = jwtUtil.getEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        if (user.getDeleted()) {
            throw new UnauthorizedException("User account has been deleted");
        }

        String newAccessToken = jwtUtil.generateAccessToken(email, user.getRole());

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }

    public void logout() {
        // No-op - JWT tokens are stateless, logout handled client-side
        // Could implement token blacklist here if needed in future
    }
}
