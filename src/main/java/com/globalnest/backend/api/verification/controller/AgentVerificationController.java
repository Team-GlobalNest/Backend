package com.globalnest.backend.api.verification.controller;

import com.globalnest.backend.api.verification.dto.request.AgentVerificationRequest;
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

@Tag(name = "중개사 인증", description = "중개사 인증 API")
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
}
