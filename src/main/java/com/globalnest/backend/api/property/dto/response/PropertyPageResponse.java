package com.globalnest.backend.api.property.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "매물 페이징 응답")
public class PropertyPageResponse {

    @Schema(description = "매물 목록")
    private List<PropertySummaryResponse> properties;

    @Schema(description = "현재 페이지 번호", example = "0")
    private Integer currentPage;

    @Schema(description = "페이지 크기", example = "20")
    private Integer pageSize;

    @Schema(description = "총 요소 개수", example = "100")
    private Long totalElements;

    @Schema(description = "총 페이지 수", example = "5")
    private Integer totalPages;

    @Schema(description = "마지막 페이지 여부", example = "false")
    private Boolean isLast;
}
