package dev.joserg.infrastructure.repository;

import dev.joserg.domain.expense.Expense;
import dev.joserg.domain.expense.ExpensesRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryExpensesRepository implements ExpensesRepository {

    private final List<Expense> expenses;

    public InMemoryExpensesRepository() {
        expenses = new ArrayList<>();
    }

    public InMemoryExpensesRepository(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public List<Expense> all() {
        return Collections.unmodifiableList(expenses);
    }

    @Override
    public void add(Expense expense) {
        expenses.add(expense);
    }
}
