package dev.joserg.application.friend;

import dev.joserg.application.friend.data.FriendData;
import dev.joserg.application.friend.data.FriendsData;
import dev.joserg.application.friend.data.NewFriendData;
import dev.joserg.domain.friend.Friend;
import dev.joserg.domain.friend.FriendsRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class FriendsService {

    @Inject
    private FriendsRepository friendsRepository;

    public FriendData create(NewFriendData newFriend) {
        var createdFriend = friendsRepository.add(new Friend(UUID.randomUUID(), newFriend.name()));
        return new FriendData(createdFriend.id(), createdFriend.name());
    }

    public FriendsData list() {
        return new FriendsData(
                friendsRepository.all().stream()
                        .map(friend -> new FriendData(friend.id(), friend.name()))
                        .collect(Collectors.toList())
        );
    }
}
