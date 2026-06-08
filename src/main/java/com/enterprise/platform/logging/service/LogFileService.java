package com.enterprise.platform.logging.service;

import com.enterprise.platform.common.exception.BusinessException;
import com.enterprise.platform.common.exception.ResourceNotFoundException;
import com.enterprise.platform.logging.web.dto.LogFileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LogFileService {

    private static final List<String> ALLOWED_EXTENSIONS = List.of(".log", ".gz");

    private final Path logDirectory;

    public LogFileService(@Value("${logging.file.path:logs}") String logPath) {
        this.logDirectory = Paths.get(logPath).toAbsolutePath().normalize();
    }

    public List<LogFileInfo> listLogFiles() {
        if (!Files.exists(logDirectory)) {
            return List.of();
        }
        try (Stream<Path> files = Files.list(logDirectory)) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(this::isAllowedLogFile)
                    .map(this::toInfo)
                    .sorted(Comparator.comparing(LogFileInfo::lastModified).reversed())
                    .toList();
        } catch (IOException ex) {
            throw new BusinessException("Unable to read log directory: " + ex.getMessage());
        }
    }

    public Resource getLogFile(String fileName) {
        Path file = resolveSafePath(fileName);
        if (!Files.exists(file) || !Files.isRegularFile(file)) {
            throw new ResourceNotFoundException("Log file not found: " + fileName);
        }
        return new FileSystemResource(file);
    }

    private Path resolveSafePath(String fileName) {
        if (fileName == null || fileName.isBlank() || fileName.contains("..")
                || fileName.contains("/") || fileName.contains("\\")) {
            throw new BusinessException("Invalid log file name");
        }
        if (!isAllowedLogFileName(fileName)) {
            throw new BusinessException("Only .log and .gz files are allowed");
        }
        Path resolved = logDirectory.resolve(fileName).normalize();
        if (!resolved.startsWith(logDirectory)) {
            throw new BusinessException("Invalid log file path");
        }
        return resolved;
    }

    private boolean isAllowedLogFile(Path path) {
        return isAllowedLogFileName(path.getFileName().toString());
    }

    private boolean isAllowedLogFileName(String fileName) {
        return ALLOWED_EXTENSIONS.stream().anyMatch(fileName::endsWith);
    }

    private LogFileInfo toInfo(Path path) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            return new LogFileInfo(
                    path.getFileName().toString(),
                    attrs.size(),
                    attrs.lastModifiedTime().toInstant()
            );
        } catch (IOException ex) {
            throw new BusinessException("Unable to read log file metadata: " + ex.getMessage());
        }
    }
}
