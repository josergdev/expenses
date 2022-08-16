package dev.joserg.domain.compensation;

import dev.joserg.domain.accounting.Amount;
import dev.joserg.domain.accounting.Relation;
import dev.joserg.domain.balance.Balance;
import dev.joserg.domain.balance.BalanceItem;
import dev.joserg.domain.balance.InMemoryBalanceProvider;
import dev.joserg.domain.friend.Friend;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComputedCompensationProviderTest {

    @Test
    void itShouldBeEmptyIfBalanceIsEmpty() {
        var calculator = new ComputedCompensationProvider(
                new InMemoryBalanceProvider(new Balance(List.of()))
        );

        var expectedCompensation = new Compensation(List.of());

        assertEquals(expectedCompensation, calculator.compensation());
    }

    @Test
    void itShouldBeEmptyIfAllZeroBalanceItems() {
        var calculator = new ComputedCompensationProvider(
                new InMemoryBalanceProvider(
                        new Balance(
                                List.of(
                                    new BalanceItem(new Friend(UUID.randomUUID(), "A"), new Amount()),
                                    new BalanceItem(new Friend(UUID.randomUUID(), "B"), new Amount())
                                )
                        )
                )
        );

        var expectedCompensation = new Compensation(List.of());

        assertEquals(expectedCompensation, calculator.compensation());
    }

    @Test
    void itShouldCompensateBalance() {
        var francisco = new Friend(UUID.randomUUID(), "Francisco Buyo");
        var alfonso = new Friend(UUID.randomUUID(), "Alfonso Pérez");
        var raul = new Friend(UUID.randomUUID(), "Raúl González");
        var jose = new Friend(UUID.randomUUID(), "José María Gutiérrez");

        var calculator = new ComputedCompensationProvider(
                new InMemoryBalanceProvider(
                        new Balance(
                                List.of(
                                        new BalanceItem(francisco, new Amount(5915)),
                                        new BalanceItem(alfonso, new Amount(2255)),
                                        new BalanceItem(raul, new Amount(-4085)),
                                        new BalanceItem(jose, new Amount(-4085))
                                )
                        )
                )
        );

        var expectedCompensation = new Compensation(
                List.of(
                        new CompensationItem(new Relation(jose, francisco), new Amount(4085)),
                        new CompensationItem(new Relation(raul, francisco), new Amount(1830)),
                        new CompensationItem(new Relation(raul, alfonso), new Amount(2255))
                )
        );

        var compensation = calculator.compensation();

        assertTrue(expectedCompensation.compensationItems().containsAll(compensation.compensationItems()));
        assertEquals(expectedCompensation.compensationItems().size(), compensation.compensationItems().size());
    }
}
