package dev.joserg.domain.expense;

import dev.joserg.domain.friend.Friend;
import dev.joserg.domain.accounting.Amount;

import java.time.LocalDateTime;

public record Expense(Friend payer, Amount amount, Description description, LocalDateTime payDate) { }
