package com.globalnest.backend.domain.property.entity;

public enum PropertyStatus {
    DRAFT,      // 임시저장
    PENDING,    // 검토대기
    ACTIVE,     // 활성화
    INACTIVE,   // 비활성화
    DELETED     // 삭제됨
}
