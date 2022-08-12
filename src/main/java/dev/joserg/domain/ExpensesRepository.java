package dev.joserg.domain;

import java.util.List;

public interface ExpensesRepository {
    List<Expense> all();
    void add(Expense expense);
}
