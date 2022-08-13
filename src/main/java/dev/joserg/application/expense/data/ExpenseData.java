package dev.joserg.application.expense.data;

import java.util.UUID;

public record ExpenseData(UUID payerId, Integer amount, String description, String dateTime) {
}
