package com.globalnest.backend.domain.user.entity;

import com.globalnest.backend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long userId;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    private String password;

    @Builder.Default
    private Boolean deleted = false;

    private LocalDateTime deletedAt;

    // --- Business Logic ---

    public void updateProfile(String firstName, String lastName, String phoneNumber, LocalDate dateOfBirth) {
        if (firstName != null)
            this.firstName = firstName;
        if (lastName != null)
            this.lastName = lastName;
        if (phoneNumber != null)
            this.phoneNumber = phoneNumber;
        if (dateOfBirth != null)
            this.dateOfBirth = dateOfBirth;
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void changeRole(Role newRole) {
        this.role = newRole;
    }

}
