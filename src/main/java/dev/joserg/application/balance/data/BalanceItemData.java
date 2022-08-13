package dev.joserg.application.balance.data;

import java.util.UUID;

public record BalanceItemData(UUID friendId, Integer amount) {
}
