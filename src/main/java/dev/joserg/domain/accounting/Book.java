package dev.joserg.domain.accounting;

import java.util.List;

public record Book(List<Note> accountingNotes) {
}
