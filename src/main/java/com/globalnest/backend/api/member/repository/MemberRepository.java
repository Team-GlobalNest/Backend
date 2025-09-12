package com.globalnest.backend.api.member.repository;

import com.globalnest.backend.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
