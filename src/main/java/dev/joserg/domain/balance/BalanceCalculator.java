package dev.joserg.domain.balance;

import dev.joserg.domain.expense.Expense;
import dev.joserg.domain.expense.ExpensesRepository;
import dev.joserg.domain.friend.Friend;
import dev.joserg.domain.friend.FriendsRepository;
import dev.joserg.domain.accounting.Amount;
import dev.joserg.domain.accounting.Book;
import dev.joserg.domain.accounting.Note;
import dev.joserg.domain.accounting.Relation;

import java.util.*;
import java.util.stream.Collectors;

public class BalanceCalculator {
    private final ExpensesRepository expensesRepository;
    private final FriendsRepository friendsRepository;

    public BalanceCalculator(ExpensesRepository expensesRepository, FriendsRepository friendsRepository) {
        this.expensesRepository = expensesRepository;
        this.friendsRepository = friendsRepository;
    }

    public Balance balance() {
        var expenses = expensesRepository.all();
        var friends = friendsRepository.all();

        var accountingBook = accountingBookFromExpensesAndFriends(expenses, friends);

        return balanceFromAccountingBookAndFriends(friends, accountingBook);
    }

    private Balance balanceFromAccountingBookAndFriends(Collection<Friend> friends, Book accountingBook) {
        var balanceMap = new HashMap<Friend, Amount>();

        friends.forEach(friend -> balanceMap.put(friend, new Amount()));

        accountingBook.accountingNotes().forEach(
                note -> {
                    balanceMap.computeIfPresent(note.relation().creditor(), (creditor, creditorAmount) -> creditorAmount.sum(note.amount()));
                    balanceMap.computeIfPresent(note.relation().debtor(), (debtor, debtorAmount) -> debtorAmount.diff(note.amount()));
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

    private Book accountingBookFromExpensesAndFriends(List<Expense> expenses, Collection<Friend> friends) {
        return new Book(
                expenses.stream()
                        .flatMap(expense -> friends.stream()
                                .map(debtor ->
                                        new Note(
                                                new Relation(debtor, expense.payer()),
                                                expense.amount().divide(friends.size())
                                        )
                                )
                        ).collect(Collectors.toList())
        );
    }
}