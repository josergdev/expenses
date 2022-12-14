package dev.joserg.domain.balance;

import dev.joserg.domain.accounting.Amount;
import dev.joserg.domain.expense.Description;
import dev.joserg.domain.expense.Expense;
import dev.joserg.domain.friend.Friend;
import dev.joserg.infrastructure.repository.memory.InMemoryExpensesRepository;
import dev.joserg.infrastructure.repository.memory.InMemoryFriendsRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ComputedBalanceProviderTest {

    @Test
    void itShouldBeEmptyBalanceIfZeroFriends() {
        var calculator = new ComputedBalanceProvider(
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

        var calculator = new ComputedBalanceProvider(
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

        var calculator = new ComputedBalanceProvider(
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
        var alfonso = new Friend(UUID.randomUUID(), "Alfonso P??rez");
        var raul = new Friend(UUID.randomUUID(), "Ra??l Gonz??lez");
        var jose = new Friend(UUID.randomUUID(), "Jos?? Mar??a Guti??rrez");

        var calculator = new ComputedBalanceProvider(
                new InMemoryExpensesRepository(
                        List.of(
                                new Expense(
                                        francisco,
                                        new Amount(100_00),
                                        new Description("Cena"),
                                        LocalDateTime.of(2022, 8, 12, 17, 0)
                                ),
                                new Expense(
                                        alfonso,
                                        new Amount(10_00),
                                        new Description("Taxi"),
                                        LocalDateTime.of(2022, 8, 12, 18, 0)
                                ),
                                new Expense(
                                        alfonso,
                                        new Amount(53_40),
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
                new BalanceItem(francisco, new Amount(59_15)),
                new BalanceItem(alfonso, new Amount(22_55)),
                new BalanceItem(jose, new Amount(-40_85)),
                new BalanceItem(raul, new Amount(-40_85))
        ));

        assertEquals(expectedBalance, calculator.balance());
    }

    @Test
    void itShouldBeZeroForAllFriendsWhenExpensesAreLessThanFriends() {
        var friendA = new Friend(UUID.randomUUID(), "A");
        var friendB = new Friend(UUID.randomUUID(), "B");

        var calculator = new ComputedBalanceProvider(
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

        var calculator = new ComputedBalanceProvider(
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