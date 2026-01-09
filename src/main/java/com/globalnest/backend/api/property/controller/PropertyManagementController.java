package com.globalnest.backend.api.property.controller;

import com.globalnest.backend.api.property.dto.request.PropertyCreateRequest;
import com.globalnest.backend.api.property.dto.request.PropertyStatusUpdateRequest;
import com.globalnest.backend.api.property.dto.request.PropertyUpdateRequest;
import com.globalnest.backend.api.property.dto.response.PropertyResponse;
import com.globalnest.backend.api.property.dto.response.PropertySummaryResponse;
import com.globalnest.backend.common.ApiResponse;
import com.globalnest.backend.domain.property.service.PropertyService;
import com.globalnest.backend.global.security.permission.PermissionChecker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "매물 관리", description = "매물 관리 API (중개사 전용)")
@RestController
@RequestMapping("/api/v1/agent/properties")
@RequiredArgsConstructor
@Validated
public class PropertyManagementController {

    private final PropertyService propertyService;
    private final PermissionChecker permissionChecker;

    @Operation(summary = "매물 등록", description = "새로운 매물을 등록합니다. (중개사 전용)")
    @PostMapping
    @PreAuthorize("hasRole('AGENT')")
    public ApiResponse<PropertyResponse> createProperty(@Valid @RequestBody PropertyCreateRequest request) {
        Long agentId = permissionChecker.getCurrentAgentId();
        PropertyResponse response = propertyService.createProperty(request, agentId);
        return ApiResponse.success(response);
    }

    @Operation(summary = "내 매물 목록 조회", description = "내가 등록한 매물 목록을 조회합니다. (중개사 전용)")
    @GetMapping("/my")
    @PreAuthorize("hasRole('AGENT')")
    public ApiResponse<List<PropertySummaryResponse>> getMyProperties() {
        Long agentId = permissionChecker.getCurrentAgentId();
        List<PropertySummaryResponse> response = propertyService.getMyProperties(agentId);
        return ApiResponse.success(response);
    }

    @Operation(summary = "매물 정보 수정", description = "매물 정보를 수정합니다. (중개사 전용, 본인 매물만)")
    @PutMapping("/{propertyId}")
    @PreAuthorize("hasRole('AGENT') and @permission.isPropertyOwner(#propertyId)")
    public ApiResponse<PropertyResponse> updateProperty(
            @PathVariable Long propertyId,
            @Valid @RequestBody PropertyUpdateRequest request) {
        Long agentId = permissionChecker.getCurrentAgentId();
        PropertyResponse response = propertyService.updateProperty(propertyId, request, agentId);
        return ApiResponse.success(response);
    }

    @Operation(summary = "매물 상태 변경", description = "매물의 상태를 변경합니다. (중개사 전용, 본인 매물만)")
    @PatchMapping("/{propertyId}/status")
    @PreAuthorize("hasRole('AGENT') and @permission.isPropertyOwner(#propertyId)")
    public ApiResponse<PropertyResponse> updatePropertyStatus(
            @PathVariable Long propertyId,
            @Valid @RequestBody PropertyStatusUpdateRequest request) {
        Long agentId = permissionChecker.getCurrentAgentId();
        PropertyResponse response = propertyService.updatePropertyStatus(propertyId, request, agentId);
        return ApiResponse.success(response);
    }

    @Operation(summary = "매물 삭제", description = "매물을 삭제합니다. (중개사 전용, 본인 매물만)")
    @DeleteMapping("/{propertyId}")
    @PreAuthorize("hasRole('AGENT') and @permission.isPropertyOwner(#propertyId)")
    public ApiResponse<Void> deleteProperty(@PathVariable Long propertyId) {
        Long agentId = permissionChecker.getCurrentAgentId();
        propertyService.deleteProperty(propertyId, agentId);
        return ApiResponse.success(null);
    }
}
