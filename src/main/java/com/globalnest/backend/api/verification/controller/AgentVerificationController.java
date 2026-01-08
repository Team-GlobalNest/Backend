package com.globalnest.backend.api.verification.controller;

import com.globalnest.backend.api.verification.dto.request.AgentVerificationRequest;
import com.globalnest.backend.api.verification.dto.request.ApprovalRequest;
import com.globalnest.backend.api.verification.dto.request.RejectionRequest;
import com.globalnest.backend.api.verification.dto.response.AgentVerificationResponse;
import com.globalnest.backend.common.ApiResponse;
import com.globalnest.backend.domain.agentverification.service.AgentVerificationService;
import com.globalnest.backend.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "중개사 인증", description = "중개사 인증 관리 API")
@RestController
@RequestMapping("/api/v1/agent-verification")
@RequiredArgsConstructor
@Validated
public class AgentVerificationController {

    private final AgentVerificationService verificationService;

    @Operation(summary = "중개사 인증 신청", description = "일반 사용자가 중개사 인증을 신청합니다.")
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<AgentVerificationResponse> submitVerification(
            @Valid @RequestBody AgentVerificationRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        AgentVerificationResponse response = verificationService.submitVerification(request, userDetails.getUserId());
        return ApiResponse.success(response);
    }

    @Operation(summary = "내 인증 상태 조회", description = "내 중개사 인증 상태를 조회합니다.")
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<AgentVerificationResponse> getMyVerification(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        AgentVerificationResponse response = verificationService.getMyVerificationStatus(userDetails.getUserId());
        return ApiResponse.success(response);
    }

    @Operation(summary = "대기 중인 인증 목록 조회", description = "승인 대기 중인 중개사 인증 목록을 조회합니다. (관리자 전용)")
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<AgentVerificationResponse>> getPending() {
        List<AgentVerificationResponse> response = verificationService.getPendingVerifications();
        return ApiResponse.success(response);
    }

    @Operation(summary = "중개사 인증 승인", description = "중개사 인증을 승인합니다. (관리자 전용)")
    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AgentVerificationResponse> approve(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        AgentVerificationResponse response = verificationService.approveVerification(id, request, userDetails.getUserId());
        return ApiResponse.success(response);
    }

    @Operation(summary = "중개사 인증 거부", description = "중개사 인증을 거부합니다. (관리자 전용)")
    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AgentVerificationResponse> reject(
            @PathVariable Long id,
            @Valid @RequestBody RejectionRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        AgentVerificationResponse response = verificationService.rejectVerification(id, request, userDetails.getUserId());
        return ApiResponse.success(response);
    }
}
