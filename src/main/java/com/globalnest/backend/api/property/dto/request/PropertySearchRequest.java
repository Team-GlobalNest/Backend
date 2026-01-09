package com.globalnest.backend.api.property.dto.request;

import com.globalnest.backend.domain.property.entity.HeatingType;
import com.globalnest.backend.domain.property.entity.PropertyStatus;
import com.globalnest.backend.domain.property.entity.PropertyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "매물 검색 요청")
public class PropertySearchRequest {

    @Schema(description = "매물 타입", example = "APARTMENT")
    private PropertyType propertyType;

    @Schema(description = "난방 타입", example = "CENTRAL")
    private HeatingType heatingType;

    @Schema(description = "도시", example = "서울특별시")
    private String city;

    @Schema(description = "구/군", example = "강남구")
    private String district;

    @Schema(description = "최소 보증금", example = "0")
    private BigDecimal minDeposit;

    @Schema(description = "최대 보증금", example = "100000000")
    private BigDecimal maxDeposit;

    @Schema(description = "최소 월세", example = "0")
    private BigDecimal minMonthlyRent;

    @Schema(description = "최대 월세", example = "2000000")
    private BigDecimal maxMonthlyRent;

    @Schema(description = "최소 면적 (㎡)", example = "20")
    private Double minRoomSize;

    @Schema(description = "최대 면적 (㎡)", example = "100")
    private Double maxRoomSize;

    @Schema(description = "최소 방 개수", example = "1")
    private Integer minNumRooms;

    @Schema(description = "최대 방 개수", example = "5")
    private Integer maxNumRooms;

    @Schema(description = "최소 화장실 개수", example = "1")
    private Integer minNumBathrooms;

    @Schema(description = "최대 화장실 개수", example = "3")
    private Integer maxNumBathrooms;

    @Schema(description = "최소 층", example = "1")
    private Integer minFloor;

    @Schema(description = "최대 층", example = "20")
    private Integer maxFloor;

    @Schema(description = "매물 상태 (기본값: ACTIVE)", example = "ACTIVE")
    @Builder.Default
    private PropertyStatus status = PropertyStatus.ACTIVE;

    @Schema(description = "활성 여부 (기본값: true)", example = "true")
    @Builder.Default
    private Boolean isAvailable = true;

    @Schema(description = "페이지 번호 (0부터 시작)", example = "0")
    @Builder.Default
    private Integer page = 0;

    @Schema(description = "페이지 크기", example = "20")
    @Builder.Default
    private Integer size = 20;

    @Schema(description = "정렬 기준 (createdAt, deposit, monthlyRent, roomSize)", example = "createdAt")
    @Builder.Default
    private String sortBy = "createdAt";

    @Schema(description = "정렬 방향 (ASC, DESC)", example = "DESC")
    @Builder.Default
    private String sortDirection = "DESC";
}
