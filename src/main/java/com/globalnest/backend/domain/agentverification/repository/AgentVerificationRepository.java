package com.globalnest.backend.domain.agentverification.repository;

import com.globalnest.backend.domain.agentverification.entity.AgentVerification;
import com.globalnest.backend.domain.user.entity.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AgentVerificationRepository extends JpaRepository<AgentVerification, Long> {

    List<AgentVerification> findByStatus(VerificationStatus status);

    Optional<AgentVerification> findByUser_UserId(Long userId);

    List<AgentVerification> findByUser_UserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT av FROM AgentVerification av WHERE av.status = :status ORDER BY av.createdAt ASC")
    List<AgentVerification> findPendingVerifications(@Param("status") VerificationStatus status);

    boolean existsByLicenseNumber(String licenseNumber);

    Optional<AgentVerification> findTopByUser_UserIdOrderByCreatedAtDesc(Long userId);
}
