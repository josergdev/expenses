package dev.joserg.application.balance;

import dev.joserg.application.balance.data.BalanceData;
import dev.joserg.application.balance.data.BalanceItemData;
import dev.joserg.domain.balance.BalanceProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class BalanceService {

    @Inject
    private BalanceProvider balanceProvider;

    public BalanceData balance() {
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

