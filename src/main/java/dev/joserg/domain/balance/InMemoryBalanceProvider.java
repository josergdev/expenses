package dev.joserg.domain.balance;

public class InMemoryBalanceProvider implements BalanceProvider {

    private final Balance balance;

    public InMemoryBalanceProvider(Balance balance) {
        this.balance = balance;
    }

    @Override
    public Balance balance() {
        return balance;
    }
}
