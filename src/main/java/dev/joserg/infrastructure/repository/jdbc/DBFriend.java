package dev.joserg.infrastructure.repository.jdbc;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.util.UUID;

@MappedEntity(value = "friend")
public record DBFriend(@Id @NonNull UUID id,
                       @NonNull String name
) {
}
