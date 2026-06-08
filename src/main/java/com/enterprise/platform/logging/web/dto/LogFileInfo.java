package com.enterprise.platform.logging.web.dto;

import java.time.Instant;

public record LogFileInfo(String fileName, long sizeBytes, Instant lastModified) {
}
