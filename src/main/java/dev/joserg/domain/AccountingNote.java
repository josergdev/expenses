package dev.joserg.domain;

public record AccountingNote(Friend debtor, Friend creditor, Amount amount) { }
