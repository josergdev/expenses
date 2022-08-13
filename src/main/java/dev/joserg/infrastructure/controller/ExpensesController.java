package dev.joserg.infrastructure.controller;

import dev.joserg.application.expense.ExpensesService;
import dev.joserg.application.expense.data.ExpenseData;
import dev.joserg.application.expense.data.ExpensesData;
import dev.joserg.application.expense.data.NewExpenseData;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/expenses")
public class ExpensesController {

    @Inject
    private ExpensesService expensesService;

    @Post
    public ExpenseData create(NewExpenseData newExpenseData) {
        return expensesService.create(newExpenseData);
    }

    @Get
    public ExpensesData list() {
        return expensesService.list();
    }
}
