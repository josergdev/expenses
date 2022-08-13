package dev.joserg.domain.balance;

import dev.joserg.domain.friend.Friend;
import dev.joserg.domain.accounting.Amount;

public record BalanceItem(Friend friend, Amount amount) {
}
