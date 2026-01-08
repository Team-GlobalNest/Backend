package com.globalnest.backend.domain.agentverification.entity;

import com.globalnest.backend.common.BaseEntity;
import com.globalnest.backend.domain.user.entity.User;
import com.globalnest.backend.domain.user.entity.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/** 중개사 인증 절차: 사용자의 신청 엔티티 */
@Entity
@Table(name = "agent_verification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AgentVerification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 신청자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /** 국가자격증(공인중개사) 번호 */
    @Column(nullable = false, length = 64)
    private String licenseNumber;

    /** 자격증 이미지(또는 스캔본) 경로/URL */
    private String licenseImageUrl;

    /** 사무소 명/주소 등 신청 시 기입한 보조 정보 */
    private String officeName;
    private String officeAddress;
    private String phoneOnOffice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private VerificationStatus status = VerificationStatus.PENDING;

    /** 1차 자동 검증 통과 여부 */
    @Builder.Default
    private Boolean autoVerified = false;

    private LocalDateTime reviewedAt;
    private String reviewerNote;

    /** 검토자 (ADMIN) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    /** 승인 처리 (2차 수동 검증) */
    public void approve(User admin, String note) {
        this.status = VerificationStatus.VERIFIED;
        this.reviewedAt = LocalDateTime.now();
        this.reviewer = admin;
        this.reviewerNote = note;
    }

    /** 거부 처리 (2차 수동 검증) */
    public void reject(User admin, String note) {
        this.status = VerificationStatus.REJECTED;
        this.reviewedAt = LocalDateTime.now();
        this.reviewer = admin;
        this.reviewerNote = note;
    }

    /** 1차 자동 검증 통과 표시 */
    public void setAutoVerified() {
        this.autoVerified = true;
    }

}
