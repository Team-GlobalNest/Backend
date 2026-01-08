package com.globalnest.backend.domain.agentverification.service;

import com.globalnest.backend.api.verification.dto.request.AgentVerificationRequest;
import com.globalnest.backend.api.verification.dto.request.ApprovalRequest;
import com.globalnest.backend.api.verification.dto.request.RejectionRequest;
import com.globalnest.backend.api.verification.dto.response.AgentVerificationResponse;
import com.globalnest.backend.domain.agentverification.entity.AgentVerification;
import com.globalnest.backend.domain.agentverification.repository.AgentVerificationRepository;
import com.globalnest.backend.domain.user.entity.Agents;
import com.globalnest.backend.domain.user.entity.Role;
import com.globalnest.backend.domain.user.entity.User;
import com.globalnest.backend.domain.user.entity.VerificationStatus;
import com.globalnest.backend.domain.user.repository.AgentRepository;
import com.globalnest.backend.domain.user.repository.UserRepository;
import com.globalnest.backend.global.exception.DuplicateVerificationException;
import com.globalnest.backend.global.exception.UserNotFoundException;
import com.globalnest.backend.global.exception.VerificationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgentVerificationService {

    private final AgentVerificationRepository verificationRepository;
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;

    @Transactional
    public AgentVerificationResponse submitVerification(AgentVerificationRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Check if already has pending verification
        Optional<AgentVerification> existing = verificationRepository.findTopByUser_UserIdOrderByCreatedAtDesc(userId);
        if (existing.isPresent() && existing.get().getStatus() == VerificationStatus.PENDING) {
            throw new DuplicateVerificationException("Verification already pending");
        }

        // Create verification
        AgentVerification verification = AgentVerification.builder()
                .user(user)
                .licenseNumber(request.getLicenseNumber())
                .licenseImageUrl(request.getLicenseImageUrl())
                .officeName(request.getOfficeName())
                .officeAddress(request.getOfficeAddress())
                .status(VerificationStatus.PENDING)
                .build();

        // Auto-verification (1차 자동 검증)
        performAutoVerification(verification);

        AgentVerification saved = verificationRepository.save(verification);

        return convertToResponse(saved);
    }

    private void performAutoVerification(AgentVerification verification) {
        // Basic auto-verification logic (1차 자동 검증)
        // 1. Check license number format (must be alphanumeric, certain length)
        // 2. Check if license number already exists
        // 3. Validate image URL is present

        boolean isValid = true;

        if (verification.getLicenseNumber() == null ||
                verification.getLicenseNumber().length() < 5) {
            isValid = false;
        }

        if (verificationRepository.existsByLicenseNumber(verification.getLicenseNumber())) {
            isValid = false;
        }

        if (verification.getLicenseImageUrl() == null ||
                verification.getLicenseImageUrl().isEmpty()) {
            isValid = false;
        }

        if (isValid) {
            verification.setAutoVerified();
        }
    }

    public AgentVerificationResponse getMyVerificationStatus(Long userId) {
        AgentVerification verification = verificationRepository.findTopByUser_UserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new VerificationNotFoundException("No verification found"));

        return convertToResponse(verification);
    }

    public List<AgentVerificationResponse> getPendingVerifications() {
        List<AgentVerification> pending = verificationRepository.findPendingVerifications(VerificationStatus.PENDING);
        return pending.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AgentVerificationResponse approveVerification(Long verificationId, ApprovalRequest request, Long adminId) {
        AgentVerification verification = verificationRepository.findById(verificationId)
                .orElseThrow(() -> new VerificationNotFoundException("Verification not found"));

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found"));

        // Approve (2차 수동 승인)
        verification.approve(admin, request.getNote());

        // Update user role to AGENT
        User user = verification.getUser();
        user.changeRole(Role.AGENT);

        // Create Agent entity
        Agents agent = Agents.builder()
                .user(user)
                .licenseNumber(verification.getLicenseNumber())
                .licenseImageUrl(verification.getLicenseImageUrl())
                .officeName(verification.getOfficeName())
                .officeAddress(verification.getOfficeAddress())
                .verificationStatus(VerificationStatus.VERIFIED)
                .verifiedAt(LocalDateTime.now())
                .build();

        agentRepository.save(agent);

        return convertToResponse(verification);
    }

    @Transactional
    public AgentVerificationResponse rejectVerification(Long verificationId, RejectionRequest request, Long adminId) {
        AgentVerification verification = verificationRepository.findById(verificationId)
                .orElseThrow(() -> new VerificationNotFoundException("Verification not found"));

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found"));

        // Reject (2차 수동 거부)
        verification.reject(admin, request.getReason());

        return convertToResponse(verification);
    }

    private AgentVerificationResponse convertToResponse(AgentVerification verification) {
        return AgentVerificationResponse.builder()
                .verificationId(verification.getId())
                .userId(verification.getUser().getUserId())
                .userName(verification.getUser().getFirstName() + " " + verification.getUser().getLastName())
                .licenseNumber(verification.getLicenseNumber())
                .licenseImageUrl(verification.getLicenseImageUrl())
                .officeName(verification.getOfficeName())
                .officeAddress(verification.getOfficeAddress())
                .status(verification.getStatus())
                .autoVerified(verification.getAutoVerified())
                .reviewedAt(verification.getReviewedAt())
                .reviewerNote(verification.getReviewerNote())
                .createdAt(verification.getCreatedAt())
                .build();
    }
}
