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

        notes.forEach(
                note -> {
                    var creditorAmount = balanceMap.getOrDefault(note.creditor(), new Amount(0));
                    balanceMap.replace(note.creditor(), creditorAmount.sum(note.amount()));
                }
        );

        notes.forEach(
                note -> {
                    var debtorAmount = balanceMap.getOrDefault(note.debtor(), new Amount(0));
                    balanceMap.replace(note.debtor(), debtorAmount.diff(note.amount()));
                }
        );

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
                        .map(debtor -> new AccountingNote(debtor, expense.payer(), expense.amount().divide(friends.size())))
                ).collect(Collectors.toList());
    }

}