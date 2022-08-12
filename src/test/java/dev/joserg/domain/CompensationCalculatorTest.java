package dev.joserg.domain;

import dev.joserg.infrastructure.InMemoryExpensesRepository;
import dev.joserg.infrastructure.InMemoryFriendsRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CompensationCalculatorTest {

    @Test
    void itShouldBeEmptyIfZeroExpenses() {
        var calculator = new CompensationCalculator(
                new InMemoryExpensesRepository(),
                new InMemoryFriendsRepository()
        );

        var expectedCompensation = new Compensation(List.of());

        assertEquals(expectedCompensation, calculator.compensation());
    }

    @Test
    void itShouldCalculateCompensationWithOneExpense() {
        var friendA = new Friend(UUID.randomUUID(), "A");
        var friendB = new Friend(UUID.randomUUID(), "B");
        var friendC = new Friend(UUID.randomUUID(), "C");

        var calculator = new CompensationCalculator(
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

        var expectedCompensation = new Compensation(List.of(
                new CompensationItem(friendB, friendA, new Amount(10)),
                new CompensationItem(friendC, friendA, new Amount(10))
        ));

        assertEquals(expectedCompensation, calculator.compensation());
    }
}