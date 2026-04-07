package com.backend.handicrafts.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@Slf4j
public class StartupLoggingConfig {

    private final DataSource dataSource;

    public StartupLoggingConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info("SERVER STARTED");

        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                log.info("DATABASE CONNECTED");
            } else {
                log.warn("DATABASE CONNECTION CHECK FAILED");
            }
        } catch (Exception ex) {
            log.warn("DATABASE CONNECTION CHECK FAILED: {}", ex.getMessage());
        }
    }
}
