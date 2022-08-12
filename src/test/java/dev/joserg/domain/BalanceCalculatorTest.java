package dev.joserg.domain;

import dev.joserg.infrastructure.InMemoryExpensesRepository;
import dev.joserg.infrastructure.InMemoryFriendsRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BalanceCalculatorTest {

    @Test
    void itShouldBeEmptyBalanceIfZeroFriends() {
        var calculator = new BalanceCalculator(
                new InMemoryExpensesRepository(),
                new InMemoryFriendsRepository()
        );

        var expectedBalance = new Balance(List.of());

        assertEquals(expectedBalance, calculator.balance());
    }

    @Test
    void itShouldBeEmptyBalanceForAllFriendsIfZeroExpenses() {
        var friendA = new Friend(UUID.randomUUID(), "A");
        var friendB = new Friend(UUID.randomUUID(), "B");

        var calculator = new BalanceCalculator(
                new InMemoryExpensesRepository(),
                new InMemoryFriendsRepository(List.of(friendA, friendB))
        );

        var expectedBalance = new Balance(List.of(
                new BalanceItem(friendA, new Amount(0)),
                new BalanceItem(friendB, new Amount(0))
        ));

        assertEquals(expectedBalance, calculator.balance());
    }

    @Test
    void itShouldCalculateBalanceForOneExpense() {
        var friendA = new Friend(UUID.randomUUID(), "A");
        var friendB = new Friend(UUID.randomUUID(), "B");
        var friendC = new Friend(UUID.randomUUID(), "C");

        var calculator = new BalanceCalculator(
                new InMemoryExpensesRepository(
                        List.of(
                                new Expense(
                                        friendA,
                                        new Amount(30),
                                        new Description("d"),
                                        LocalDateTime.of(2022, 8, 12, 17, 0)
                                )
                        )
                ),
                new InMemoryFriendsRepository(
                        List.of(friendA, friendB, friendC)
                )
        );

        var expectedBalance = new Balance(List.of(
                new BalanceItem(friendA, new Amount(30)),
                new BalanceItem(friendB, new Amount(-10)),
                new BalanceItem(friendC, new Amount(-10))
        ));

        assertEquals(expectedBalance, calculator.balance());
    }
}