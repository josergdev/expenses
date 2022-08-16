package dev.joserg.infrastructure.repository.jpa;

import dev.joserg.domain.friend.Friend;
import dev.joserg.domain.friend.FriendsRepository;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Primary
@Singleton
@Transactional
public class FriendsEntityRepository implements FriendsRepository {

    private final EntityManager entityManager;

    public FriendsEntityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Collection<Friend> all() {
        return entityManager.createQuery(
                        "SELECT f FROM FriendEntity as f",
                        FriendEntity.class
                ).getResultStream()
                .map(fe -> new Friend(fe.id(), fe.name()))
                .toList();
    }

    @Override
    public Friend add(Friend friend) {
        entityManager.persist(new FriendEntity(friend.id(), friend.name()));
        return friend;
    }

    @Override
    public Optional<Friend> find(UUID friendId) {
        return Optional.ofNullable(entityManager.find(FriendEntity.class, friendId))
                .map(fe -> new Friend(fe.id(), fe.name()));
    }
}
