package com.globalnest.backend.global.security.permission;

import com.globalnest.backend.domain.property.repository.PropertyRepository;
import com.globalnest.backend.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("permission")
@RequiredArgsConstructor
public class PermissionChecker {

    private final PropertyRepository propertyRepository;

    /**
     * SecurityContext에서 현재 인증된 사용자 정보를 추출합니다.
     */
    private CustomUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalStateException("인증되지 않은 사용자입니다.");
        }
        return (CustomUserDetails) auth.getPrincipal();
    }

    /**
     * 현재 로그인한 사용자의 userId를 반환합니다.
     */
    public Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    /**
     * 현재 로그인한 사용자의 agentId를 반환합니다.
     * AGENT가 아닌 경우 null을 반환합니다.
     */
    public Long getCurrentAgentId() {
        return getCurrentUser().getAgentId();
    }

    /**
     * 현재 사용자가 특정 매물의 소유자인지 확인합니다.
     * 
     * @PreAuthorize SpEL 표현식에서 사용됩니다.
     */
    public boolean isPropertyOwner(Long propertyId) {
        Long agentId = getCurrentAgentId();
        if (agentId == null) {
            return false;
        }
        return propertyRepository.existsByPropertyIdAndAgent_AgentId(propertyId, agentId);
    }
}
