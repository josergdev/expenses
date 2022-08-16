package dev.joserg.application.friend.data;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record NewFriendData(String name) {
}
