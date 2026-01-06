package com.globalnest.backend.domain.agentverification.entity;

import com.globalnest.backend.common.BaseEntity;
import com.globalnest.backend.domain.member.entity.Users;
import com.globalnest.backend.domain.member.entity.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/** 중개사 인증 절차: 사용자의 신청 엔티티 */
@Entity
@Table(name = "agent_verification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentVerification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 신청자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

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
    private VerificationStatus status;

    private LocalDateTime reviewedAt;
    private String reviewerNote;

}
