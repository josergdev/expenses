package dev.joserg.application.balance;

import dev.joserg.application.balance.data.BalanceData;
import dev.joserg.application.balance.data.BalanceItemData;
import dev.joserg.domain.balance.ComputedBalanceProvider;
import dev.joserg.domain.expense.ExpensesRepository;
import dev.joserg.domain.friend.FriendsRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class BalanceService {

    @Inject
    private ExpensesRepository expensesRepository;
    @Inject
    private FriendsRepository friendsRepository;

    public BalanceData balance() {
        var balanceProvider = new ComputedBalanceProvider(expensesRepository, friendsRepository);

        var balance = balanceProvider.balance();

        return new BalanceData(
                balance.balanceItems().stream()
                        .map(balanceItem -> new BalanceItemData(
                                balanceItem.friend().id(),
                                balanceItem.amount().value()
                        )).toList()
        );
    }
}

