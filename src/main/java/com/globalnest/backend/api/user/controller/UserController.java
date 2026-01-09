package com.globalnest.backend.api.user.controller;

import com.globalnest.backend.api.user.dto.request.UserUpdateRequest;
import com.globalnest.backend.api.user.dto.response.UserResponse;
import com.globalnest.backend.common.ApiResponse;
import com.globalnest.backend.domain.user.service.UserService;
import com.globalnest.backend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자", description = "사용자 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserService userService;

    @Operation(summary = "내 프로필 조회", description = "로그인한 사용자의 프로필을 조회합니다.")
    @GetMapping("/me")
    public ApiResponse<UserResponse> getMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse response = userService.getMyProfile(userDetails.getUserId());
        return ApiResponse.success(response);
    }

    @Operation(summary = "내 프로필 수정", description = "로그인한 사용자의 프로필을 수정합니다.")
    @PutMapping("/me")
    public ApiResponse<UserResponse> updateMyProfile(
            @Valid @RequestBody UserUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse response = userService.updateMyProfile(userDetails.getUserId(), request);
        return ApiResponse.success(response);
    }

    @Operation(summary = "회원 탈퇴", description = "로그인한 사용자의 계정을 삭제합니다.")
    @DeleteMapping("/me")
    public ApiResponse<Void> deleteMyAccount(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteMyAccount(userDetails.getUserId());
        return ApiResponse.success(null);
    }
}
