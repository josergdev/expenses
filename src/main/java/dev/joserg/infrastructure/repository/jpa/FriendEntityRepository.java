package dev.joserg.infrastructure.repository.jpa;

import dev.joserg.domain.friend.Friend;
import dev.joserg.domain.friend.FriendsRepository;
import io.micronaut.context.annotation.Primary;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Primary
@Transactional
public class FriendEntityRepository implements FriendsRepository {

    private final EntityManager entityManager;

    public FriendEntityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Collection<Friend> all() {
        return entityManager.createQuery(
                        "select fe from FriendEntity as fe",
                        FriendEntity.class
                ).getResultStream()
                .map(fe -> new Friend(UUID.fromString(fe.getId()), fe.getName()))
                .toList();
    }

    @Override
    public Friend add(Friend friend) {
        entityManager.persist(new FriendEntity(friend));
        return friend;
    }

    @Override
    public Optional<Friend> find(UUID friendId) {
        return Optional.ofNullable(entityManager.find(FriendEntity.class, friendId.toString()))
                .map(fe -> new Friend(UUID.fromString(fe.getId()), fe.getName()));
    }
}
