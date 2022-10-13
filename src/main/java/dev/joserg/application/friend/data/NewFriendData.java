package dev.joserg.application.friend.data;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotEmpty;

@Introspected
public record NewFriendData(@NotEmpty String name) {
}
