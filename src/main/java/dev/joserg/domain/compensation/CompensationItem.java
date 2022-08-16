package dev.joserg.domain.compensation;

import dev.joserg.domain.accounting.Amount;
import dev.joserg.domain.accounting.Relation;

public record CompensationItem(Relation relation, Amount amount) {
}
