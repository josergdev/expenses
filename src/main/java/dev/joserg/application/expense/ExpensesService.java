package dev.joserg.application.expense;

import dev.joserg.application.expense.data.ExpenseData;
import dev.joserg.application.expense.data.ExpensesData;
import dev.joserg.application.expense.data.NewExpenseData;
import dev.joserg.application.friend.exception.FriendNotFoundException;
import dev.joserg.domain.accounting.Amount;
import dev.joserg.domain.expense.Description;
import dev.joserg.domain.expense.Expense;
import dev.joserg.domain.expense.ExpensesRepository;
import dev.joserg.domain.friend.FriendsRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Singleton
public class ExpensesService {

    @Inject
    private ExpensesRepository expensesRepository;
    @Inject
    private FriendsRepository friendsRepository;

    public ExpenseData create(NewExpenseData newExpenseData) throws FriendNotFoundException {
        var payer = friendsRepository.find(newExpenseData.payerId())
                .orElseThrow(() -> new FriendNotFoundException(newExpenseData.payerId()));

        var createdExpense = expensesRepository.add(
                new Expense(
                        payer,
                        new Amount(newExpenseData.amount()),
                        new Description(newExpenseData.description()),
                        LocalDateTime.now(ZoneOffset.UTC)
                )
        );

        return new ExpenseData(
                createdExpense.payer().id(),
                createdExpense.amount().value(),
                createdExpense.description().value(),
                DateTimeFormatter.ISO_INSTANT.format(createdExpense.payDate().toInstant(ZoneOffset.UTC))
        );
    }

    public ExpensesData list() {
        return new ExpensesData(
                expensesRepository.all().stream()
                        .map(expense -> new ExpenseData(
                                        expense.payer().id(),
                                        expense.amount().value(),
                                        expense.description().value(),
                                        DateTimeFormatter.ISO_INSTANT.format(expense.payDate().toInstant(ZoneOffset.UTC))
                                )
                        ).toList()
        );
    }
}
