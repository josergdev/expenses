package dev.joserg.domain.accounting;

import java.util.Comparator;

public record Amount(Integer value) implements Comparable<Amount> {

    public Amount() {
        this(0);
    }
    
    public static Amount zero() {
        return new Amount();
    }

    public static Amount negate(Amount amount) {
        return new Amount(- amount.value());
    }

    public Amount divide(Integer divisor) {
        return new Amount(value() / divisor);
    }

    public Amount sum(Amount amount) {
        return new Amount(value() + amount.value());
    }

    public Amount diff(Amount amount) {
        return new Amount(value() - amount.value());
    }

    @Override
    public int compareTo(Amount comparedAmount) {
        return Comparator.comparing(Amount::value).compare(this, comparedAmount);
    }
}
