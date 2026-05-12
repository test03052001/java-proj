package com.enterprise.platform.user.web.dto;

import java.time.Instant;

public record UserResponse(Long id, String email, String displayName, boolean active, Instant createdAt) {
}
