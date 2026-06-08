package com.enterprise.platform.logging.web;

import com.enterprise.platform.common.api.ApiResponse;
import com.enterprise.platform.logging.service.LogFileService;
import com.enterprise.platform.logging.web.dto.LogFileInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
@Tag(name = "Logs", description = "Generated application log files")
public class LogController {

    private final LogFileService logFileService;

    @GetMapping("/files")
    @Operation(summary = "List generated log files")
    public ApiResponse<List<LogFileInfo>> listFiles() {
        return ApiResponse.ok(logFileService.listLogFiles());
    }

    @GetMapping("/files/{fileName}")
    @Operation(summary = "Download a log file")
    public ResponseEntity<Resource> download(@PathVariable String fileName) {
        Resource resource = logFileService.getLogFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
}
