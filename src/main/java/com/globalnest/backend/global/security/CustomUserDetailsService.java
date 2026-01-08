package com.globalnest.backend.global.security;

import com.globalnest.backend.domain.user.entity.Role;
import com.globalnest.backend.domain.user.entity.User;
import com.globalnest.backend.domain.user.repository.AgentRepository;
import com.globalnest.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (user.getDeleted()) {
            throw new UsernameNotFoundException("User account has been deleted");
        }

        Long agentId = null;
        if (user.getRole() == Role.AGENT) {
            agentId = agentRepository.findByUser_UserId(user.getUserId())
                    .map(agent -> agent.getAgentId())
                    .orElse(null);
        }

        return CustomUserDetails.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .agentId(agentId)
                .build();
    }
}
