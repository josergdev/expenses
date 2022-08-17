package dev.joserg.infrastructure.repository.mysql;

import dev.joserg.domain.accounting.Amount;
import dev.joserg.domain.expense.Description;
import dev.joserg.domain.expense.Expense;
import dev.joserg.domain.expense.ExpensesRepository;
import dev.joserg.domain.friend.Friend;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jooq.DSLContext;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Singleton
@Transactional
public class MySqlExpensesRepository implements ExpensesRepository {
    @Inject
    private DSLContext dslContext;

    @Override
    public List<Expense> all() {
        return dslContext
                .select(
                        field("expense.payer_id"),
                        field("friend.name"),
                        field("expense.amount"),
                        field("expense.description"),
                        field("expense.created_at")
                ).from("expense")
                .join("friend").on(field("friend.id").eq(field("expense.payer_id")))
                .fetch()
                .map(r -> new Expense(
                        new Friend(UUID.fromString((String) r.component1()), (String) r.component2()),
                        new Amount(Math.toIntExact((Long) r.component3())),
                        new Description((String) r.component4()),
                        (LocalDateTime) r.component5()
                ));
    }

    @Override
    public Expense add(Expense expense) {
        dslContext
                .insertInto(
                        table("expense"),
                        field("expense.payer_id"),
                        field("expense.amount"),
                        field("expense.description"),
                        field("expense.created_at")
                )
                .values(expense.payer().id().toString(), expense.amount().value(), expense.description().value(), expense.payDate())
                .execute();
        return expense;
    }
}