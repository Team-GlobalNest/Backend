package com.globalnest.backend.domain.user.repository;

import com.globalnest.backend.domain.user.entity.Agents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Agents, Long> {
}
