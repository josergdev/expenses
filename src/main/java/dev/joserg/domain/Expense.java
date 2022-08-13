package dev.joserg.domain;

import java.time.LocalDateTime;

record Description(String value) { }
public record Expense(Friend payer, Amount amount, Description description, LocalDateTime payDate) { }
