package com.backend.handicrafts.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRoleSchemaConfig {

    private final JdbcTemplate jdbcTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void relaxUserRoleColumn() {
        try {
            jdbcTemplate.execute("ALTER TABLE users MODIFY COLUMN role VARCHAR(255) NULL");
        } catch (Exception ignored) {
            log.debug("users.role nullable migration skipped: {}", ignored.getMessage());
        }

        try {
            jdbcTemplate.execute("ALTER TABLE users MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT");
        } catch (Exception ignored) {
            log.debug("users.id auto_increment migration skipped: {}", ignored.getMessage());
        }

        try {
            // Widen first so uppercase enum values like SHIPPED can be written safely.
            jdbcTemplate.execute("ALTER TABLE orders MODIFY COLUMN status VARCHAR(50) NOT NULL");
        } catch (Exception ignored) {
            log.debug("orders.status widen migration skipped: {}", ignored.getMessage());
        }

        try {
            jdbcTemplate.execute("""
                UPDATE orders
                SET status = CASE UPPER(TRIM(status))
                    WHEN 'PENDING' THEN 'PENDING'
                    WHEN 'CONFIRMED' THEN 'CONFIRMED'
                    WHEN 'SHIPPED' THEN 'SHIPPED'
                    WHEN 'DELIVERED' THEN 'DELIVERED'
                    WHEN 'CANCELLED' THEN 'CANCELLED'
                    ELSE 'PENDING'
                END
            """);
        } catch (Exception ignored) {
            log.debug("orders.status normalization skipped: {}", ignored.getMessage());
        }

        log.info("Schema compatibility checks completed for users.role/users.id/orders.status");
    }
}