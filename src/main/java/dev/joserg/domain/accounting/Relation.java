package dev.joserg.domain.accounting;

import dev.joserg.domain.friend.Friend;

public record Relation(Friend debtor, Friend creditor) {
}
