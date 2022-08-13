package dev.joserg.application.friend.exception;

import java.util.UUID;

public class FriendNotFoundException extends RuntimeException {

    private final UUID friendId;

    public FriendNotFoundException(UUID friendId) {

        this.friendId = friendId;
    }

    public UUID friendId() {
        return this.friendId;
    }
}
