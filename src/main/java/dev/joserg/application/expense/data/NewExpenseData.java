package dev.joserg.application.expense.data;

import java.util.UUID;

public record NewExpenseData(UUID payerId, Integer amount, String description) {
}
