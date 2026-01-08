package com.globalnest.backend.api.property.dto.response;

import com.globalnest.backend.domain.property.entity.PropertyStatus;
import com.globalnest.backend.domain.property.entity.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertySummaryResponse {

    private Long propertyId;
    private String title;
    private BigDecimal deposit;
    private BigDecimal monthlyRent;
    private String city;
    private String district;
    private PropertyType propertyType;
    private Double roomSize;
    private Integer numRooms;
    private PropertyStatus status;
    private LocalDateTime createdAt;
}
