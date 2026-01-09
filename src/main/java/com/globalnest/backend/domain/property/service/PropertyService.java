package com.globalnest.backend.domain.property.service;

import com.globalnest.backend.api.property.dto.request.PropertyCreateRequest;
import com.globalnest.backend.api.property.dto.request.PropertySearchRequest;
import com.globalnest.backend.api.property.dto.request.PropertyStatusUpdateRequest;
import com.globalnest.backend.api.property.dto.request.PropertyUpdateRequest;
import com.globalnest.backend.api.property.dto.response.PropertyPageResponse;
import com.globalnest.backend.api.property.dto.response.PropertyResponse;
import com.globalnest.backend.api.property.dto.response.PropertySummaryResponse;
import com.globalnest.backend.domain.user.entity.Agents;
import com.globalnest.backend.domain.user.repository.AgentRepository;
import com.globalnest.backend.domain.property.entity.*;
import com.globalnest.backend.domain.property.repository.OptionListRepository;
import com.globalnest.backend.domain.property.repository.PropertyOptionListRepository;
import com.globalnest.backend.domain.property.repository.PropertyRepository;
import com.globalnest.backend.domain.property.repository.PropertySpecification;
import com.globalnest.backend.global.exception.ForbiddenException;
import com.globalnest.backend.global.exception.PropertyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final AgentRepository agentRepository;
    private final OptionListRepository optionListRepository;
    private final PropertyOptionListRepository propertyOptionListRepository;

    @Transactional
    public PropertyResponse createProperty(PropertyCreateRequest request, Long agentId) {
        // Agent 조회
        Agents agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new PropertyNotFoundException("중개사를 찾을 수 없습니다"));

        // Property 엔티티 생성
        Property property = Property.builder()
                .agentId(agent)
                .title(request.getTitle())
                .description(request.getDescription())
                .deposit(request.getDeposit())
                .monthlyRent(request.getMonthlyRent())
                .maintenanceFee(request.getMaintenanceFee())
                .builtDate(request.getBuiltDate())
                .roomSize(request.getRoomSize())
                .numRooms(request.getNumRooms())
                .numBathrooms(request.getNumBathrooms())
                .floor(request.getFloor())
                .heatingType(request.getHeatingType())
                .propertyType(request.getPropertyType())
                .minStayMonths(request.getMinStayMonths())
                .city(request.getCity())
                .district(request.getDistrict())
                .address(request.getAddress())
                .status(PropertyStatus.DRAFT)
                .isAvailable(true)
                .build();

        Property savedProperty = propertyRepository.save(property);

        // 옵션 처리
        buildAndSavePropertyOptions(savedProperty, request.getOptionIds(), request.getIsFullOption());

        return convertToResponse(savedProperty);
    }

    public PropertyResponse getProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("매물을 찾을 수 없습니다"));

        return convertToResponse(property);
    }

    public PropertyPageResponse searchProperties(PropertySearchRequest searchRequest) {
        // 페이징 및 정렬 설정
        Sort sort = Sort.by(
                searchRequest.getSortDirection().equalsIgnoreCase("DESC")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC,
                searchRequest.getSortBy()
        );

        Pageable pageable = PageRequest.of(
                searchRequest.getPage(),
                searchRequest.getSize(),
                sort
        );

        // Specification을 사용하여 동적 쿼리 실행
        Specification<Property> spec = PropertySpecification.withFilters(searchRequest);
        Page<Property> propertyPage = propertyRepository.findAll(spec, pageable);

        // PropertySummaryResponse로 변환
        List<PropertySummaryResponse> summaryResponses = propertyPage.getContent().stream()
                .map(this::convertToSummaryResponse)
                .collect(Collectors.toList());

        return PropertyPageResponse.builder()
                .properties(summaryResponses)
                .currentPage(propertyPage.getNumber())
                .pageSize(propertyPage.getSize())
                .totalElements(propertyPage.getTotalElements())
                .totalPages(propertyPage.getTotalPages())
                .isLast(propertyPage.isLast())
                .build();
    }

    public List<PropertySummaryResponse> getMyProperties(Long agentId) {
        Agents agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new PropertyNotFoundException("중개사를 찾을 수 없습니다"));

        List<Property> properties = propertyRepository.findAll()
                .stream()
                .filter(p -> p.getAgentId().getAgentId().equals(agentId))
                .collect(Collectors.toList());

        return properties.stream()
                .map(this::convertToSummaryResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PropertyResponse updateProperty(Long propertyId, PropertyUpdateRequest request, Long agentId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("매물을 찾을 수 없습니다"));

        // 소유권 검증
        validateOwnership(property, agentId);

        // 필드 업데이트
        property.updateDetails(
                request.getTitle(),
                request.getDescription(),
                request.getDeposit(),
                request.getMonthlyRent(),
                request.getMaintenanceFee(),
                request.getBuiltDate(),
                request.getRoomSize(),
                request.getNumRooms(),
                request.getNumBathrooms(),
                request.getFloor(),
                request.getHeatingType(),
                request.getPropertyType(),
                request.getMinStayMonths(),
                request.getCity(),
                request.getDistrict(),
                request.getAddress());

        // 옵션 업데이트
        if (request.getOptionIds() != null || request.getIsFullOption() != null) {
            // 기존 옵션 삭제
            propertyOptionListRepository.deleteByProperty_PropertyId(propertyId);
            // 새 옵션 추가
            buildAndSavePropertyOptions(property, request.getOptionIds(), request.getIsFullOption());
        }

        return convertToResponse(property);
    }

    @Transactional
    public PropertyResponse updatePropertyStatus(Long propertyId, PropertyStatusUpdateRequest request, Long agentId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("매물을 찾을 수 없습니다"));

        // 소유권 검증
        validateOwnership(property, agentId);

        // 상태 변경
        property.changeStatus(request.getStatus());

        return convertToResponse(property);
    }

    @Transactional
    public void deleteProperty(Long propertyId, Long agentId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("매물을 찾을 수 없습니다"));

        // 소유권 검증
        validateOwnership(property, agentId);

        // Soft delete
        property.changeStatus(PropertyStatus.DELETED);
    }

    // Private helper methods

    private void validateOwnership(Property property, Long agentId) {
        if (!property.getAgentId().getAgentId().equals(agentId)) {
            throw new ForbiddenException("해당 매물을 수정할 권한이 없습니다");
        }
    }

    private void buildAndSavePropertyOptions(Property property, List<Long> optionIds, Boolean isFullOption) {
        List<PropertyOptionList> propertyOptions = new ArrayList<>();

        if (Boolean.TRUE.equals(isFullOption)) {
            // 모든 옵션 자동 추가
            List<OptionList> allOptions = optionListRepository.findAll();
            for (OptionList option : allOptions) {
                PropertyOptionList propertyOption = PropertyOptionList.builder()
                        .property(property)
                        .optionList(option)
                        .build();
                propertyOptions.add(propertyOption);
            }
        } else if (optionIds != null && !optionIds.isEmpty()) {
            // 선택된 옵션만 추가
            for (Long optionId : optionIds) {
                OptionList option = optionListRepository.findById(optionId)
                        .orElseThrow(() -> new PropertyNotFoundException("옵션을 찾을 수 없습니다: " + optionId));
                PropertyOptionList propertyOption = PropertyOptionList.builder()
                        .property(property)
                        .optionList(option)
                        .build();
                propertyOptions.add(propertyOption);
            }
        }

        if (!propertyOptions.isEmpty()) {
            propertyOptionListRepository.saveAll(propertyOptions);
        }
    }

    private PropertyResponse convertToResponse(Property property) {
        // 옵션 조회
        List<PropertyResponse.OptionDto> options = property.getOptionLists().stream()
                .map(optionList -> PropertyResponse.OptionDto.builder()
                        .optionId(optionList.getOptionList().getOptionId())
                        .optionName(optionList.getOptionList().getOptionName())
                        .build())
                .collect(Collectors.toList());

        // Agent 정보
        PropertyResponse.AgentSummaryDto agentDto = PropertyResponse.AgentSummaryDto.builder()
                .agentId(property.getAgentId().getAgentId())
                .officeName(property.getAgentId().getOfficeName())
                .profileImageUrl(property.getAgentId().getProfileImageUrl())
                .build();

        return PropertyResponse.builder()
                .propertyId(property.getPropertyId())
                .agent(agentDto)
                .title(property.getTitle())
                .description(property.getDescription())
                .deposit(property.getDeposit())
                .monthlyRent(property.getMonthlyRent())
                .maintenanceFee(property.getMaintenanceFee())
                .builtDate(property.getBuiltDate())
                .roomSize(property.getRoomSize())
                .numRooms(property.getNumRooms())
                .numBathrooms(property.getNumBathrooms())
                .floor(property.getFloor())
                .heatingType(property.getHeatingType())
                .propertyType(property.getPropertyType())
                .minStayMonths(property.getMinStayMonths())
                .city(property.getCity())
                .district(property.getDistrict())
                .address(property.getAddress())
                .isAvailable(property.getIsAvailable())
                .status(property.getStatus())
                .options(options)
                .createdAt(property.getCreatedAt())
                .updatedAt(property.getUpdatedAt())
                .build();
    }

    private PropertySummaryResponse convertToSummaryResponse(Property property) {
        return PropertySummaryResponse.builder()
                .propertyId(property.getPropertyId())
                .title(property.getTitle())
                .deposit(property.getDeposit())
                .monthlyRent(property.getMonthlyRent())
                .city(property.getCity())
                .district(property.getDistrict())
                .propertyType(property.getPropertyType())
                .roomSize(property.getRoomSize())
                .numRooms(property.getNumRooms())
                .status(property.getStatus())
                .createdAt(property.getCreatedAt())
                .build();
    }
}
