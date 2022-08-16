package dev.joserg.domain.balance;

import dev.joserg.domain.expense.Expense;
import dev.joserg.domain.expense.ExpensesRepository;
import dev.joserg.domain.friend.Friend;
import dev.joserg.domain.friend.FriendsRepository;
import dev.joserg.domain.accounting.Amount;
import dev.joserg.domain.accounting.Book;
import dev.joserg.domain.accounting.Note;
import dev.joserg.domain.accounting.Relation;
import jakarta.inject.Singleton;

import java.util.*;

@Singleton
public class ComputedBalanceProvider implements BalanceProvider {
    private final ExpensesRepository expensesRepository;
    private final FriendsRepository friendsRepository;

    public ComputedBalanceProvider(ExpensesRepository expensesRepository, FriendsRepository friendsRepository) {
        this.expensesRepository = expensesRepository;
        this.friendsRepository = friendsRepository;
    }

    @Override
    public Balance balance() {
        var expenses = expensesRepository.all();
        var friends = friendsRepository.all();

        var accountingBook = accountingBookFromExpensesAndFriends(expenses, friends);

        return balanceFromAccountingBookAndFriends(accountingBook, friends);
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
                        ).toList()
        );
    }

    private Balance balanceFromAccountingBookAndFriends(Book accountingBook, Collection<Friend> friends) {
        var balanceMap = new HashMap<Friend, Amount>();

        friends.forEach(friend -> balanceMap.put(friend, Amount.zero()));

        accountingBook.accountingNotes().forEach(
                note -> {
                    balanceMap.computeIfPresent(note.relation().creditor(), (creditor, creditorAmount) -> creditorAmount.sum(note.amount()));
                    balanceMap.computeIfPresent(note.relation().debtor(), (debtor, debtorAmount) -> debtorAmount.diff(note.amount()));
                }
        );

        var balanceItems = balanceMap.entrySet().stream()
                .map(entry -> new BalanceItem(entry.getKey(), entry.getValue()))
                .sorted(
                        Comparator.comparing(BalanceItem::amount).reversed()
                                .thenComparing((BalanceItem balanceItem) -> balanceItem.friend().name())
                ).toList();

        return new Balance(balanceItems);
    }
}