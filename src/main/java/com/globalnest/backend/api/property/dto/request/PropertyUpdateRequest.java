package com.globalnest.backend.api.property.dto.request;

import com.globalnest.backend.domain.property.entity.HeatingType;
import com.globalnest.backend.domain.property.entity.PropertyType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyUpdateRequest {

    @Size(max = 100, message = "제목은 100자 이하여야 합니다")
    private String title;

    @Size(max = 1000, message = "설명은 1000자 이하여야 합니다")
    private String description;

    @DecimalMin(value = "0.0", message = "보증금은 0 이상이어야 합니다")
    private BigDecimal deposit;

    @DecimalMin(value = "0.0", message = "월세는 0 이상이어야 합니다")
    private BigDecimal monthlyRent;

    @DecimalMin(value = "0.0", message = "관리비는 0 이상이어야 합니다")
    private BigDecimal maintenanceFee;

    private LocalDate builtDate;

    @Min(value = 1, message = "면적은 1 이상이어야 합니다")
    private Double roomSize;

    @Min(value = 1, message = "방 개수는 1 이상이어야 합니다")
    private Integer numRooms;

    @Min(value = 1, message = "화장실 개수는 1 이상이어야 합니다")
    private Integer numBathrooms;

    private Integer floor;

    private HeatingType heatingType;

    private PropertyType propertyType;

    @Min(value = 1, message = "최소 거주 개월은 1 이상이어야 합니다")
    private Integer minStayMonths;

    private String city;

    private String district;

    private String address;

    private List<Long> optionIds;

    private Boolean isFullOption;
}
