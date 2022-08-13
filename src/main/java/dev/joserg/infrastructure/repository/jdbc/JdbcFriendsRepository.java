package dev.joserg.infrastructure.repository.jdbc;

import dev.joserg.domain.friend.Friend;
import dev.joserg.domain.friend.FriendsRepository;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Primary
@Singleton
public class JdbcFriendsRepository implements FriendsRepository {
    @Inject
    private DBFriendsRepository dbFriendsRepository;

    private static Friend mapping(DBFriend dbFriend) {
        return new Friend(dbFriend.id(), dbFriend.name());
    }

    private static DBFriend mapping(Friend friend) {
        return new DBFriend(friend.id(), friend.name());
    }

    @Override
    public Collection<Friend> all() {
        return StreamSupport.stream(dbFriendsRepository.findAll().spliterator(), false)
                .map(JdbcFriendsRepository::mapping)
                .toList();
    }

    @Override
    public Friend add(Friend friend) {
        return mapping(dbFriendsRepository.save(mapping(friend)));
    }

    @Override
    public Optional<Friend> find(UUID friendId) {
        var dbFriend = dbFriendsRepository.findById(friendId);
        if (dbFriend.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(mapping(dbFriend.get()));
    }
}
