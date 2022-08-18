package dev.joserg.domain.shared.clock;

import io.micronaut.context.annotation.Primary;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Primary
public class SystemClock implements Clock {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
