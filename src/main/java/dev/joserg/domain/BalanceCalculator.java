package dev.joserg.domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        var balanceMap = new HashMap<Friend, Amount>();

        friends.forEach(friend -> balanceMap.put(friend, new Amount(0)));

        expenses.forEach(
                expense -> {
                    var payerAmount = balanceMap.getOrDefault(expense.payer(), new Amount(0));
                    balanceMap.replace(expense.payer(), sumAmounts(payerAmount, expense.amount()));
                }
        );

        var notes = expenses.stream()
                .flatMap(expense -> accountingNotesFromExpense(expense, friends))
                .collect(Collectors.toList());

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

    private Stream<AccountingNote> accountingNotesFromExpense(Expense expense, Collection<Friend> friends) {
        return friends.stream()
                .filter(friend -> !friend.equals(expense.payer()))
                .map(debtor -> new AccountingNote(debtor, expense.payer(), new Amount(expense.amount().value() / friends.size())));
    }

    private Amount sumAmounts(Amount a, Amount b) {
        return new Amount(a.value() + b.value());
    }

    private Amount diffAmounts(Amount a, Amount b) {
        return new Amount(a.value() - b.value());
    }
}