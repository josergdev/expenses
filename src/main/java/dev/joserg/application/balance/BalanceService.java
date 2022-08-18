package dev.joserg.application.balance;

import dev.joserg.application.balance.data.BalanceData;
import dev.joserg.application.balance.data.BalanceItemData;
import dev.joserg.domain.balance.BalanceProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Singleton
public class BalanceService {

    @Inject
    private BalanceProvider balanceProvider;

    public BalanceData balance() {
        return balanceProvider.balance()
                .balanceItems().stream()
                .map(balanceItem ->
                        new BalanceItemData(
                                balanceItem.friend().id(),
                                balanceItem.amount().value()
                        )
                ).collect(collectingAndThen(toList(), BalanceData::new));
    }
}

