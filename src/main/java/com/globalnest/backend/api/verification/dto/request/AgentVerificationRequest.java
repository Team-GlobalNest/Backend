package com.globalnest.backend.api.verification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentVerificationRequest {

    @NotBlank(message = "License number is required")
    @Size(min = 5, max = 64, message = "License number must be between 5 and 64 characters")
    private String licenseNumber;

    @NotBlank(message = "License image URL is required")
    private String licenseImageUrl;

    @NotBlank(message = "Office name is required")
    private String officeName;

    @NotBlank(message = "Office address is required")
    private String officeAddress;
}
