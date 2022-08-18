package dev.joserg.domain.shared.uuid;

import io.micronaut.context.annotation.Primary;

import java.util.UUID;

@Primary
public class JavaUuidGenerator implements UuidGenerator {
    @Override
    public UUID random() {
        return UUID.randomUUID();
    }
}
