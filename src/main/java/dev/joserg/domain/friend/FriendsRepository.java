package dev.joserg.domain.friend;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface FriendsRepository {
    Collection<Friend> all();
    Friend add(Friend friend);
    Optional<Friend> find(UUID friendId);
}
