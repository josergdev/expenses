package dev.joserg.infrastructure.repository.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "friend")
public class FriendEntity {
    @Id
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    public FriendEntity() {
    }

    public FriendEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }
}

