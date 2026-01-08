package com.globalnest.backend.domain.user.service;

import com.globalnest.backend.api.user.dto.request.UserUpdateRequest;
import com.globalnest.backend.api.user.dto.response.UserResponse;
import com.globalnest.backend.domain.user.entity.Role;
import com.globalnest.backend.domain.user.entity.User;
import com.globalnest.backend.domain.user.repository.AgentRepository;
import com.globalnest.backend.domain.user.repository.UserRepository;
import com.globalnest.backend.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final AgentRepository agentRepository;

    public UserResponse getMyProfile(Long userId) {
        User user = userRepository.findByUserIdAndDeletedFalse(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return convertToResponse(user);
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findByUserIdAndDeletedFalse(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return convertToResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findByDeletedFalse().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateMyProfile(Long userId, UserUpdateRequest request) {
        User user = userRepository.findByUserIdAndDeletedFalse(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.updateProfile(
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getDateOfBirth());

        return convertToResponse(user);
    }

    @Transactional
    public void deleteMyAccount(Long userId) {
        User user = userRepository.findByUserIdAndDeletedFalse(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        performSoftDelete(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findByUserIdAndDeletedFalse(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        performSoftDelete(user);
    }

    private void performSoftDelete(User user) {
        user.delete();
    }

    private UserResponse convertToResponse(User user) {
        Long agentId = null;
        if (user.getRole() == Role.AGENT) {
            agentId = agentRepository.findByUser_UserId(user.getUserId())
                    .map(agent -> agent.getAgentId())
                    .orElse(null);
        }

        return UserResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .role(user.getRole())
                .agentId(agentId)
                .createdAt(user.getCreatedAt())
                .build();
    }
}
