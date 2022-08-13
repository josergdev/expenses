package dev.joserg.domain.accounting;

public record Amount(Integer value) {

    public Amount() {
        this(0);
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
}
