package dev.joserg.domain;

import java.util.List;

record BalanceItem(Friend friend, Amount amount) {}
public record Balance(List<BalanceItem> balanceItem) {}