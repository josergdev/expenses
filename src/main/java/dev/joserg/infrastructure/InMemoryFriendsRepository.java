package dev.joserg.infrastructure;

import dev.joserg.domain.Friend;
import dev.joserg.domain.FriendsRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class InMemoryFriendsRepository implements FriendsRepository {

    private Collection<Friend> friends;

    InMemoryFriendsRepository() {
        friends = new ArrayList<>();
    }

    InMemoryFriendsRepository(Collection<Friend> friends) {
        this.friends = friends;
    }

    @Override
    public Collection<Friend> all() {
        return Collections.unmodifiableCollection(friends);
    }

    @Override
    public void add(Friend friend) {
        friends.add(friend);
    }
}
