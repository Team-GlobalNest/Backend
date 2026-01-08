package com.globalnest.backend.api.auth.controller;

import com.globalnest.backend.api.auth.dto.request.LoginRequest;
import com.globalnest.backend.api.auth.dto.request.RefreshTokenRequest;
import com.globalnest.backend.api.auth.dto.response.LoginResponse;
import com.globalnest.backend.api.auth.dto.response.TokenResponse;
import com.globalnest.backend.common.ApiResponse;
import com.globalnest.backend.domain.auth.service.AuthService;
import com.globalnest.backend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증", description = "로그인, 로그아웃, 토큰 갱신 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    @Value("${jwt.refresh-expiration}")
    private long refreshExpirationTime;

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다.")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ApiResponse.success(response);
    }

    @Operation(summary = "토큰 갱신", description = "Refresh Token으로 새로운 Access Token을 발급받습니다.")
    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        TokenResponse response = authService.refresh(request);
        return ApiResponse.success(response);
    }

    @Operation(summary = "로그아웃", description = "로그아웃 처리를 합니다.")
    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<AuthDto.LogoutResponse> logout(
            @AuthenticationPrincipal User user,
            HttpServletResponse response) {
        authService.logout(user.getId());

        // Refresh Token 쿠키 삭제
        deleteRefreshTokenCookie(response);

        return ApiResponse.success(AuthDto.LogoutResponse.success());
    }
}
