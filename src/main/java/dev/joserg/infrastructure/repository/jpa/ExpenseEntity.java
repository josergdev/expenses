package dev.joserg.infrastructure.repository.jpa;

import dev.joserg.domain.expense.Expense;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneOffset;

@Entity
@Table(name = "expense")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payer_id", nullable = false)
    private FriendEntity friendEntity;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public ExpenseEntity() {
    }

    public ExpenseEntity(FriendEntity friendEntity, Long amount, String description, Instant createdAt) {
        this.friendEntity = friendEntity;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    public ExpenseEntity(Expense expense) {
        this(
                new FriendEntity(expense.payer()),
                expense.amount().value().longValue(),
                expense.description().value(),
                expense.payDate().atOffset(ZoneOffset.UTC).toInstant()
        );
    }

    public Long getId() {
        return id;
    }

    public FriendEntity getFriendEntity() {
        return friendEntity;
    }

    public Long getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
