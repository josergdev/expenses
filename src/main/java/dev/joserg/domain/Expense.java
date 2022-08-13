package dev.joserg.domain;

import java.time.LocalDateTime;

record Amount(Integer value) {
    Amount divide(Integer divisor) {
        return new Amount(value() / divisor);
    }

    Amount sum(Amount b) {
        return new Amount(value() + b.value());
    }

    Amount diff(Amount b) {
        return new Amount(value() - b.value());
    }
}
record Description(String value) { }
public record Expense(Friend payer, Amount amount, Description description, LocalDateTime payDate) { }
