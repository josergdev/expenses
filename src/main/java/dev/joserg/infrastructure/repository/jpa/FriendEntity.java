package dev.joserg.infrastructure.repository.jpa;

import dev.joserg.domain.friend.Friend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "friend")
public class FriendEntity {
    @Id
    private String id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    public FriendEntity() {
    }

    public FriendEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public FriendEntity(Friend friend) {
        this(friend.id().toString(), friend.name());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

