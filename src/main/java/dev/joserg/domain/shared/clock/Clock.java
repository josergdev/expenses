package dev.joserg.domain.shared.clock;

import java.time.LocalDateTime;

public interface Clock {
    LocalDateTime now();
}
