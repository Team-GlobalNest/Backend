package com.globalnest.backend.domain.user.entity;

import com.globalnest.backend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Agents extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agent_seq_gen")
    @SequenceGenerator(
            name = "agent_seq_gen",
            sequenceName = "agent_seq",
            allocationSize = 1
    )
    private Long agentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 자격증 번호
    private String licenseNumber;

    // 자격증 발급일
    private LocalDate dateOfIssue;

    // 자격증 이미지 url
    private String licenseImageUrl;

    // 회사 이름
    private String officeName;

    // 회사 주소
    private String officeAddress;

    // 프로필 이미지 url
    private String profileImageUrl;

    // 소개
    private String introduction;

    private String officeHours;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;

    private LocalDateTime verifiedAt;

}
