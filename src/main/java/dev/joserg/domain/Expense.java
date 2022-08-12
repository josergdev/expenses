package dev.joserg.domain;

import java.time.LocalDateTime;

record Amount(Integer value) implements Comparable<Amount>{
    @Override
    public int compareTo(Amount o) {
        return o.value().compareTo(value());
    }
}
record Description(String value) {}
public record Expense(Friend payer, Amount amount, Description description, LocalDateTime payDate) { }
