package com.globalnest.backend.domain.property.repository;

import com.globalnest.backend.api.property.dto.request.PropertySearchRequest;
import com.globalnest.backend.domain.property.entity.Property;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PropertySpecification {

    public static Specification<Property> withFilters(PropertySearchRequest searchRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 매물 타입 필터
            if (searchRequest.getPropertyType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("propertyType"), searchRequest.getPropertyType()));
            }

            // 난방 타입 필터
            if (searchRequest.getHeatingType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("heatingType"), searchRequest.getHeatingType()));
            }

            // 도시 필터
            if (searchRequest.getCity() != null && !searchRequest.getCity().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("city"), searchRequest.getCity()));
            }

            // 구/군 필터
            if (searchRequest.getDistrict() != null && !searchRequest.getDistrict().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("district"), searchRequest.getDistrict()));
            }

            // 보증금 범위 필터
            if (searchRequest.getMinDeposit() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("deposit"), searchRequest.getMinDeposit()));
            }
            if (searchRequest.getMaxDeposit() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("deposit"), searchRequest.getMaxDeposit()));
            }

            // 월세 범위 필터
            if (searchRequest.getMinMonthlyRent() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("monthlyRent"), searchRequest.getMinMonthlyRent()));
            }
            if (searchRequest.getMaxMonthlyRent() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("monthlyRent"), searchRequest.getMaxMonthlyRent()));
            }

            // 면적 범위 필터
            if (searchRequest.getMinRoomSize() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("roomSize"), searchRequest.getMinRoomSize()));
            }
            if (searchRequest.getMaxRoomSize() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("roomSize"), searchRequest.getMaxRoomSize()));
            }

            // 방 개수 범위 필터
            if (searchRequest.getMinNumRooms() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("numRooms"), searchRequest.getMinNumRooms()));
            }
            if (searchRequest.getMaxNumRooms() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("numRooms"), searchRequest.getMaxNumRooms()));
            }

            // 화장실 개수 범위 필터
            if (searchRequest.getMinNumBathrooms() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("numBathrooms"), searchRequest.getMinNumBathrooms()));
            }
            if (searchRequest.getMaxNumBathrooms() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("numBathrooms"), searchRequest.getMaxNumBathrooms()));
            }

            // 층 범위 필터
            if (searchRequest.getMinFloor() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("floor"), searchRequest.getMinFloor()));
            }
            if (searchRequest.getMaxFloor() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("floor"), searchRequest.getMaxFloor()));
            }

            // 매물 상태 필터
            if (searchRequest.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), searchRequest.getStatus()));
            }

            // 활성 여부 필터
            if (searchRequest.getIsAvailable() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isAvailable"), searchRequest.getIsAvailable()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
