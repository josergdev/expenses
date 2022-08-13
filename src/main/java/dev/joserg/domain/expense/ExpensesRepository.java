package dev.joserg.domain.expense;

import java.util.List;

public interface ExpensesRepository {
    List<Expense> all();
    Expense add(Expense expense);
}
