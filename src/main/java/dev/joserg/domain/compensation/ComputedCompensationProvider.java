package dev.joserg.domain.compensation;

import dev.joserg.domain.accounting.Amount;
import dev.joserg.domain.accounting.Relation;
import dev.joserg.domain.balance.BalanceItem;
import dev.joserg.domain.balance.BalanceProvider;
import jakarta.inject.Singleton;

import java.util.*;

@Singleton
public class ComputedCompensationProvider implements CompensationProvider {

    private BalanceProvider balanceProvider;

    public ComputedCompensationProvider(BalanceProvider balanceProvider) {
        this.balanceProvider = balanceProvider;
    }

    @Override
    public Compensation compensation() {
        var balance = balanceProvider.balance();

        var creditors = new ArrayList<BalanceItem>();
        var debtors = new ArrayList<BalanceItem>();

        balance.balanceItems().forEach(
                balanceItem -> {
                    if (balanceItem.amount().value() < 0) {
                        debtors.add(balanceItem);
                    } else if (balanceItem.amount().value() > 0) {
                        creditors.add(balanceItem);
                    }
                }
        );

        var compensationItems = new ArrayList<CompensationItem>();

        while (!debtors.isEmpty()) {
            var greaterDebtor = debtors.get(debtors.size() - 1 );
            var greaterCreditor = creditors.get(0);

            creditors.remove(greaterCreditor);
            debtors.remove(greaterDebtor);

            var diffAmount = greaterCreditor.amount().value() + greaterDebtor.amount().value();
            if (diffAmount == 0 ) {
                compensationItems.add(new CompensationItem(new Relation(greaterDebtor.friend(), greaterCreditor.friend()), greaterCreditor.amount()));
            } else if(diffAmount > 0) {
                compensationItems.add(new CompensationItem(new Relation(greaterDebtor.friend(), greaterCreditor.friend()), new Amount(- greaterDebtor.amount().value())));
                creditors.add(new BalanceItem(greaterCreditor.friend(), new Amount(diffAmount)));
                creditors.sort(Comparator.comparing((BalanceItem balanceItem) -> balanceItem.amount().value()).reversed());
            } else {
                compensationItems.add(new CompensationItem(new Relation(greaterDebtor.friend(), greaterCreditor.friend()), greaterCreditor.amount()));
                debtors.add(new BalanceItem(greaterDebtor.friend(), new Amount(diffAmount)));
                debtors.sort(Comparator.comparing((BalanceItem balanceItem) -> balanceItem.amount().value()).reversed());
            }
        }

        return new Compensation(compensationItems);
    }
}
