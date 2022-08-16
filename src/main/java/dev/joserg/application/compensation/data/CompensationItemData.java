package dev.joserg.application.compensation.data;

import java.util.UUID;

public record CompensationItemData(UUID debtorId, UUID creditorId, String amount) {
}
