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
    void itShouldCalculateBalanceWithOneExpense() {
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
                new BalanceItem(friendA, new Amount(20)),
                new BalanceItem(friendB, new Amount(-10)),
                new BalanceItem(friendC, new Amount(-10))
        ));

        assertEquals(expectedBalance, calculator.balance());
    }

    @Test
    void itShouldCalculateBalanceWithMultipleExpenses() {
        var francisco = new Friend(UUID.randomUUID(), "Francisco Buyo");
        var alfonso = new Friend(UUID.randomUUID(), "Alfonso Pérez");
        var raul = new Friend(UUID.randomUUID(), "Raúl González");
        var jose = new Friend(UUID.randomUUID(), "José María Gutiérrez");

        var calculator = new BalanceCalculator(
                new InMemoryExpensesRepository(
                        List.of(
                                new Expense(
                                        francisco,
                                        new Amount(10_000),
                                        new Description("Cena"),
                                        LocalDateTime.of(2022, 8, 12, 17, 0)
                                ),
                                new Expense(
                                        alfonso,
                                        new Amount(1_000),
                                        new Description("Taxi"),
                                        LocalDateTime.of(2022, 8, 12, 18, 0)
                                ),
                                new Expense(
                                        alfonso,
                                        new Amount(5_340),
                                        new Description("Compra"),
                                        LocalDateTime.of(2022, 8, 12, 19, 0)
                                )
                        )
                ),
                new InMemoryFriendsRepository(
                        List.of(francisco, alfonso, raul, jose)
                )
        );

        var expectedBalance = new Balance(List.of(
                new BalanceItem(francisco, new Amount(5_915)),
                new BalanceItem(alfonso, new Amount(2_255)),
                new BalanceItem(jose, new Amount(-4_085)),
                new BalanceItem(raul, new Amount(-4_085))
        ));

        assertEquals(expectedBalance, calculator.balance());
    }

    @Test
    void itShouldBeZeroForAllFriendsWhenExpensesAreLessThanFriends() {
        var friendA = new Friend(UUID.randomUUID(), "A");
        var friendB = new Friend(UUID.randomUUID(), "B");

        var calculator = new BalanceCalculator(
                new InMemoryExpensesRepository(
                        List.of(
                                new Expense(
                                        friendA,
                                        new Amount(1),
                                        new Description("d"),
                                        LocalDateTime.of(2022, 8, 12, 17, 0)
                                )
                        )
                ),
                new InMemoryFriendsRepository(
                        List.of(friendA, friendB)
                )
        );

        var expectedBalance = new Balance(List.of(
                new BalanceItem(friendA, new Amount(0)),
                new BalanceItem(friendB, new Amount(0))
        ));

        assertEquals(expectedBalance, calculator.balance());
    }

    @Test
    void itShouldChargeTheRemainderToThePayer() {
        var friendA = new Friend(UUID.randomUUID(), "A");
        var friendB = new Friend(UUID.randomUUID(), "B");
        var friendC = new Friend(UUID.randomUUID(), "C");

        var calculator = new BalanceCalculator(
                new InMemoryExpensesRepository(
                        List.of(
                                new Expense(
                                        friendA,
                                        new Amount(3),
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
                new BalanceItem(friendA, new Amount(2)),
                new BalanceItem(friendB, new Amount(-1)),
                new BalanceItem(friendC, new Amount(-1))
        ));

        assertEquals(expectedBalance, calculator.balance());
    }

}