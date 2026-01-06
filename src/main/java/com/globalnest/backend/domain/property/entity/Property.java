package com.globalnest.backend.domain.property.entity;

import com.globalnest.backend.common.BaseEntity;
import com.globalnest.backend.domain.member.entity.Agents;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Property extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "property_seq_gen")
    @SequenceGenerator(
            name = "property_seq_gen",
            sequenceName = "property_seq",
            allocationSize = 1
    )
    private Long propertyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private Agents agentId;

    // 제목
    private String title;

    // 설명
    private String description;

    // 보증금 - 최대 10자리 숫자 중 소수점 이하 2자리까지 허용
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal deposit;

    // 월세 - 최대 10자리 숫자 중 소수점 이하 2자리까지 허용
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal monthlyRent;

    // 관리비 - 최대 10자리 숫자 중 소수점 이하 2자리까지 허용, 기본값 0
    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal maintenanceFee = BigDecimal.ZERO;

    // 건축일
    private LocalDate builtDate;

    // 면적
    private Double roomSize;

    // 방 개수
    @Builder.Default
    private Integer numRooms = 1;

    // 화장실 개수
    @Builder.Default
    private Integer numBathrooms = 1;

    // 층
    private Integer floor;

    @Enumerated(EnumType.STRING)
    private HeatingType heatingType;

    // 집 유형
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Builder.Default
    private Integer minStayMonths = 6;

    // 도시
    private String city;

    // 구/군
    private String district;

    // 상세 주소
    private String address;

    // 활성 상태
    @Builder.Default
    private Boolean isAvailable = true;

    // 옵션 리스트
    @OneToMany(mappedBy = "property")
    @Builder.Default
    private List<PropertyOptionList> optionLists = new ArrayList<>();

}
