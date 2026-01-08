package com.globalnest.backend.api.property.dto.request;

import com.globalnest.backend.domain.property.entity.PropertyStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStatusUpdateRequest {

    @NotNull(message = "상태는 필수입니다")
    private PropertyStatus status;
}
