package dev.joserg.domain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CompensationCalculator {

    private final ExpensesRepository expensesRepository;
    private final FriendsRepository friendsRepository;

    CompensationCalculator(ExpensesRepository expensesRepository, FriendsRepository friendsRepository) {
        this.expensesRepository = expensesRepository;
        this.friendsRepository = friendsRepository;
    }

    Compensation compensation() {
        var expenses = expensesRepository.all();
        var friends = friendsRepository.all();

        var notes = accountingNotesFromExpenses(expenses, friends);

        var compensationItems = notes.stream()
                .collect(Collectors.groupingBy(note -> new Pair(note.debtor(), note.creditor())))
                .entrySet().stream()
                .map(entry ->
                        new CompensationItem(
                            entry.getKey().debtor(),
                            entry.getKey().creditor(),
                            new Amount(
                                    entry.getValue().stream()
                                    .map(AccountingNote::amount)
                                    .map(Amount::value)
                                    .reduce(0, Integer::sum)
                            )
                        )
                ).filter(item -> !item.debtor().equals(item.creditor()))
                .collect(Collectors.toList());

        return new Compensation(compensationItems);
    }

    private List<AccountingNote> accountingNotesFromExpenses(List<Expense> expenses, Collection<Friend> friends) {
        return expenses.stream()
                .flatMap(expense -> friends.stream()
                        .map(debtor -> new AccountingNote(debtor, expense.payer(), new Amount(expense.amount().value() / friends.size()))))
                .collect(Collectors.toList());
    }

    public static record Pair(Friend debtor, Friend creditor) {}
}