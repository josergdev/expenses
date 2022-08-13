package dev.joserg.infrastructure.repository;

import dev.joserg.domain.friend.Friend;
import dev.joserg.domain.friend.FriendsRepository;
import jakarta.inject.Singleton;

import java.util.*;

@Singleton
public class InMemoryFriendsRepository implements FriendsRepository {

    private final Collection<Friend> friends;

    public InMemoryFriendsRepository() {
        friends = new ArrayList<>();
    }

    public InMemoryFriendsRepository(Collection<Friend> friends) {
        this.friends = friends;
    }

    @Override
    public Collection<Friend> all() {
        return Collections.unmodifiableCollection(friends);
    }

    @Override
    public Friend add(Friend friend) {
        friends.add(friend);
        return friend;
    }

    @Override
    public Optional<Friend> find(UUID friendId) {
        return friends.stream()
                .filter(friend -> friendId.equals(friend.id()))
                .findFirst();
    }
}
