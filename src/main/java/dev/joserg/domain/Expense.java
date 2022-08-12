package dev.joserg.domain;

import java.time.LocalDateTime;

record Amount(Integer value) { }
record Description(String value) { }
public record Expense(Friend payer, Amount amount, Description description, LocalDateTime payDate) { }
