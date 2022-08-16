package dev.joserg.application.compensation;

import dev.joserg.application.compensation.data.CompensationData;
import dev.joserg.application.compensation.data.CompensationItemData;
import dev.joserg.domain.compensation.CompensationProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Singleton
public class CompensationService {

    @Inject
    CompensationProvider compensationProvider;

    public CompensationData compensation() {
        return compensationProvider.compensation()
                .compensationItems().stream()
                .map(compensationItem ->
                        new CompensationItemData(
                                compensationItem.relation().debtor().id(),
                                compensationItem.relation().creditor().id(),
                                compensationItem.amount().value().toString()
                        )
                ).collect(collectingAndThen(toList(), CompensationData::new));
    }
}
