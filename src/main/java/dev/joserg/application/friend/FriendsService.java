package dev.joserg.application.friend;

import dev.joserg.application.friend.data.FriendData;
import dev.joserg.application.friend.data.FriendsData;
import dev.joserg.application.friend.data.NewFriendData;
import dev.joserg.domain.friend.Friend;
import dev.joserg.domain.friend.FriendsRepository;
import dev.joserg.domain.shared.uuid.UuidGenerator;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Singleton
public class FriendsService {

    @Inject
    private FriendsRepository friendsRepository;
    @Inject
    private UuidGenerator uuidGenerator;

    public FriendData create(NewFriendData newFriend) {
        var createdFriend = friendsRepository.add(new Friend(uuidGenerator.random(), newFriend.name()));
        return new FriendData(createdFriend.id(), createdFriend.name());
    }

    public FriendsData list() {
        return friendsRepository
                .all().stream()
                .map(friend -> new FriendData(friend.id(), friend.name()))
                .collect(collectingAndThen(toList(), FriendsData::new));
    }
}
