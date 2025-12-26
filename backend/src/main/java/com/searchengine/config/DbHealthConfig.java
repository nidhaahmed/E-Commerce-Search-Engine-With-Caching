package com.searchengine.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DbHealthConfig implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().withDetail("db", "configured").build();
    }
}
