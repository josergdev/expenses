package dev.joserg.domain;

import java.util.Collection;

record CompensationItem(Friend debtor, Friend creditor, Amount amount) { }
public record Compensation(Collection<CompensationItem> compensationItems) { }
