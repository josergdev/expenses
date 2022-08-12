package dev.joserg.domain;

import java.util.*;
import java.util.stream.Collectors;

public class BalanceCalculator {
    private final ExpensesRepository expensesRepository;
    private final FriendsRepository friendsRepository;

    BalanceCalculator(ExpensesRepository expensesRepository, FriendsRepository friendsRepository) {
        this.expensesRepository = expensesRepository;
        this.friendsRepository = friendsRepository;
    }

    Balance balance() {
        var expenses = expensesRepository.all();
        var friends = friendsRepository.all();

        var notes = accountingNotesFromExpenses(expenses, friends);

        var balanceMap = new HashMap<Friend, Amount>();

        friends.forEach(friend -> balanceMap.put(friend, new Amount(0)));

        expenses.forEach(
                expense -> {
                    var payerAmount = balanceMap.getOrDefault(expense.payer(), new Amount(0));
                    balanceMap.replace(expense.payer(), sumAmounts(payerAmount, expense.amount()));
                }
        );

        notes.forEach(note -> {
            var debtorAmount = balanceMap.getOrDefault(note.debtor(), new Amount(0));
            balanceMap.replace(note.debtor(), diffAmounts(debtorAmount, note.amount()));
        });

        var balanceItems = balanceMap.entrySet().stream()
                .map(entry -> new BalanceItem(entry.getKey(), entry.getValue()))
                .sorted(
                        Comparator.comparing((BalanceItem balanceItem) -> balanceItem.amount().value()).reversed()
                                .thenComparing((BalanceItem balanceItem) -> balanceItem.friend().name())
                ).collect(Collectors.toList());


        return new Balance(balanceItems);
    }

    private List<AccountingNote> accountingNotesFromExpenses(List<Expense> expenses, Collection<Friend> friends) {
        return expenses.stream()
                .flatMap(expense -> friends.stream()
                                .map(debtor -> new AccountingNote(debtor, expense.payer(), new Amount(expense.amount().value() / friends.size()))))
                .collect(Collectors.toList());
    }

    private Amount sumAmounts(Amount a, Amount b) {
        return new Amount(a.value() + b.value());
    }

    private Amount diffAmounts(Amount a, Amount b) {
        return new Amount(a.value() - b.value());
    }
}