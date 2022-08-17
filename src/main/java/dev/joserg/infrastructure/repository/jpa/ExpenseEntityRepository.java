package dev.joserg.infrastructure.repository.jpa;

import dev.joserg.domain.accounting.Amount;
import dev.joserg.domain.expense.Description;
import dev.joserg.domain.expense.Expense;
import dev.joserg.domain.expense.ExpensesRepository;
import dev.joserg.domain.friend.Friend;
import io.micronaut.context.annotation.Primary;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Primary
@Transactional
public class ExpenseEntityRepository implements ExpensesRepository {

    private final EntityManager entityManager;

    public ExpenseEntityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Expense> all() {
        return entityManager.createQuery("select ee from ExpenseEntity as ee", ExpenseEntity.class)
                .getResultStream()
                .map(ee ->
                        new Expense(
                                new Friend(UUID.fromString(ee.getFriendEntity().getId()), ee.getFriendEntity().getName()),
                                new Amount(ee.getAmount().intValue()),
                                new Description(ee.getDescription()),
                                LocalDateTime.ofInstant(ee.getCreatedAt(), ZoneOffset.UTC)
                        )
                ).toList();
    }

    @Override
    public Expense add(Expense expense) {
        entityManager.persist(new ExpenseEntity(expense));
        return expense;
    }
}
