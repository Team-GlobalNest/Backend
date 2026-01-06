package com.globalnest.backend.domain.Inquiry.entity;

import com.globalnest.backend.domain.member.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "inquiry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDate createdAt;

    private LocalDate respondedAt;

    @Column(nullable = false)
    @Builder.Default
    private boolean isResponded = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responded_by_admin_id")
    private Users respondedByAdmin;

    @Column(columnDefinition = "TEXT")
    private String adminResponse;
}