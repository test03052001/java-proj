package com.enterprise.platform.user.web;

import com.enterprise.platform.common.api.ApiResponse;
import com.enterprise.platform.user.service.UserService;
import com.enterprise.platform.user.web.dto.CreateUserRequest;
import com.enterprise.platform.user.web.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Account directory")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "List users")
    public ApiResponse<List<UserResponse>> list() {
        return ApiResponse.ok(userService.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public ApiResponse<UserResponse> get(@PathVariable Long id) {
        return ApiResponse.ok(userService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register user")
    public ApiResponse<UserResponse> create(@Valid @RequestBody CreateUserRequest body) {
        return ApiResponse.ok(userService.create(body));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft-deactivate user")
    public void deactivate(@PathVariable Long id) {
        userService.deactivate(id);
    }
}
