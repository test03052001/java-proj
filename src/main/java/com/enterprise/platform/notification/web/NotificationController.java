package com.enterprise.platform.notification.web;

import com.enterprise.platform.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications", description = "Channel preferences (stub)")
public class NotificationController {

    @GetMapping("/preferences")
    @Operation(summary = "Get notification preferences (in-memory demo)")
    public ApiResponse<Map<String, Object>> preferences() {
        return ApiResponse.ok(Map.of(
                "email", true,
                "sms", false,
                "push", true
        ));
    }

    @PutMapping("/preferences")
    @Operation(summary = "Update preferences (no-op demo)")
    public ApiResponse<Map<String, Object>> update(@RequestBody Map<String, Object> body) {
        return ApiResponse.ok(body);
    }
}
