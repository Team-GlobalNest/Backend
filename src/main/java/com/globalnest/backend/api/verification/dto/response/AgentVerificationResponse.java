package com.globalnest.backend.api.verification.dto.response;

import com.globalnest.backend.domain.user.entity.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentVerificationResponse {

    private Long verificationId;
    private Long userId;
    private String userName;
    private String licenseNumber;
    private String licenseImageUrl;
    private String officeName;
    private String officeAddress;
    private VerificationStatus status;
    private Boolean autoVerified;
    private LocalDateTime reviewedAt;
    private String reviewerNote;
    private LocalDateTime createdAt;
}
