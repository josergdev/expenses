package dev.joserg.domain.friend;

import java.util.Collection;

public interface FriendsRepository {
    Collection<Friend> all();
    void add(Friend friend);
}
