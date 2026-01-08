package com.globalnest.backend.api.property.dto.response;

import com.globalnest.backend.domain.property.entity.HeatingType;
import com.globalnest.backend.domain.property.entity.PropertyStatus;
import com.globalnest.backend.domain.property.entity.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponse {

    private Long propertyId;
    private AgentSummaryDto agent;
    private String title;
    private String description;
    private BigDecimal deposit;
    private BigDecimal monthlyRent;
    private BigDecimal maintenanceFee;
    private LocalDate builtDate;
    private Double roomSize;
    private Integer numRooms;
    private Integer numBathrooms;
    private Integer floor;
    private HeatingType heatingType;
    private PropertyType propertyType;
    private Integer minStayMonths;
    private String city;
    private String district;
    private String address;
    private Boolean isAvailable;
    private PropertyStatus status;
    private List<OptionDto> options;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AgentSummaryDto {
        private Long agentId;
        private String officeName;
        private String profileImageUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OptionDto {
        private Long optionId;
        private String optionName;
    }
}
