package com.globalnest.backend.api.property.controller;

import com.globalnest.backend.api.property.dto.request.PropertySearchRequest;
import com.globalnest.backend.api.property.dto.response.PropertyPageResponse;
import com.globalnest.backend.api.property.dto.response.PropertyResponse;
import com.globalnest.backend.common.ApiResponse;
import com.globalnest.backend.domain.property.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "매물", description = "매물 조회 API")
@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
@Validated
public class PropertyController {

    private final PropertyService propertyService;

    @Operation(summary = "매물 검색", description = "필터 조건에 따라 매물을 검색합니다.")
    @GetMapping("/search")
    public ApiResponse<PropertyPageResponse> searchProperties(
            @ModelAttribute PropertySearchRequest searchRequest) {
        PropertyPageResponse response = propertyService.searchProperties(searchRequest);
        return ApiResponse.success(response);
    }

    @Operation(summary = "매물 상세 조회", description = "매물의 상세 정보를 조회합니다.")
    @GetMapping("/{propertyId}")
    public ApiResponse<PropertyResponse> getProperty(@PathVariable Long propertyId) {
        PropertyResponse response = propertyService.getProperty(propertyId);
        return ApiResponse.success(response);
    }
}
