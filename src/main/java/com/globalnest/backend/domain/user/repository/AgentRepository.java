package com.globalnest.backend.domain.user.repository;

import com.globalnest.backend.domain.user.entity.Agents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agents, Long> {

    Optional<Agents> findByUser_UserId(Long userId);

    Optional<Agents> findByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumber(String licenseNumber);
}
