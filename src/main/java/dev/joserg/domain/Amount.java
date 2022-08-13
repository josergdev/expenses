package dev.joserg.domain;

record Amount(Integer value) {

    public Amount() {
        this(0);
    }

    Amount divide(Integer divisor) {
        return new Amount(value() / divisor);
    }

    Amount sum(Amount amount) {
        return new Amount(value() + amount.value());
    }

    Amount diff(Amount amount) {
        return new Amount(value() - amount.value());
    }
}
