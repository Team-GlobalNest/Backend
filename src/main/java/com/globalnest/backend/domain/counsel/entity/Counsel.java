package com.globalnest.backend.domain.counsel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "Counsel")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Counsel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counselId", nullable = false)
    private Long counselId;

    @Column(name = "propertyId")
    private Long propertyId;

    @Column(name = "foreignerId")
    private Long foreignerId;

    @Column(name = "agentId")
    private Long agentId;

    @Column(name = "channel", length = 255)
    private String channel;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

}