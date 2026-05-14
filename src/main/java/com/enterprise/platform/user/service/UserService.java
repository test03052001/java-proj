package com.enterprise.platform.user.service;

import com.enterprise.platform.common.exception.BusinessException;
import com.enterprise.platform.common.exception.ResourceNotFoundException;
import com.enterprise.platform.user.domain.User;
import com.enterprise.platform.user.repository.UserRepository;
import com.enterprise.platform.user.web.dto.CreateUserRequest;
import com.enterprise.platform.user.web.dto.UpdateUserRequest;
import com.enterprise.platform.user.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserResponse> listAll() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> search(String term) {
        return userRepository.findByEmailContainingIgnoreCaseOrDisplayNameContainingIgnoreCase(term, term).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        return userRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email already registered");
        }
        User user = User.builder()
                .email(request.email())
                .displayName(request.displayName())
                .createdAt(Instant.now())
                .active(true)
                .build();
        return toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        if (userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new BusinessException("Email already registered");
        }
        user.setEmail(request.email());
        user.setDisplayName(request.displayName());
        user.setActive(request.active());
        return toResponse(user);
    }

    @Transactional
    public void deactivate(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        user.setActive(false);
    }

    @Transactional
    public UserResponse activate(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        user.setActive(true);
        return toResponse(user);
    }

    public User getEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    private UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getEmail(), u.getDisplayName(), u.isActive(), u.getCreatedAt());
    }
}
