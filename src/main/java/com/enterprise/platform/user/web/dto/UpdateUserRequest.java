package com.enterprise.platform.user.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(max = 120) String displayName,
        boolean active
) {
}
