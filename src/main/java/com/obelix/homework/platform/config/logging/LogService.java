package com.obelix.homework.platform.config.logging;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogService {
    private static final Logger log = LoggerFactory.getLogger(LogService.class);
    private static final String LOG_FILE_PATH = "logs/application.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void info(String message) {
        log(Level.INFO, message);
        log.info(message);
    }

    public void error(String message) {
        log(Level.ERROR, message);
        log.error(message);
    }

    public void warn(String message) {
        log(Level.WARN, message);
        log.warn(message);
    }

    private void log(Level level, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(String.format("[%s] [%s] %s%n", LocalDateTime.now().format(FORMATTER), level.toString(), message));
        } catch (IOException e) {
            log.error("Failed to write log entry", e);
        }
    }

    @PostConstruct
    private void ensureLogFileExists() {
        try {
            File logFile = new File(LOG_FILE_PATH);
            if (!logFile.getParentFile().exists()) {
                logFile.getParentFile().mkdirs();
            }
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
        } catch (IOException e) {
            log.error("Could not create log file", e);
        }
    }
}