package com.globalnest.backend.api.verification.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RejectionRequest {

    @NotBlank(message = "Rejection reason is required")
    private String reason;
}
