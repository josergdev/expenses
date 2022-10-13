package dev.joserg.infrastructure.controller;

import dev.joserg.application.friend.FriendsService;
import dev.joserg.application.friend.data.FriendData;
import dev.joserg.application.friend.data.FriendsData;
import dev.joserg.application.friend.data.NewFriendData;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import jakarta.inject.Inject;

import javax.validation.Valid;

@Controller
public class FriendsController {

    @Inject
    private FriendsService friendsService;

    @Post("/friends")
    @Status(HttpStatus.CREATED)
    public FriendData create(@Valid NewFriendData newFriend) {
        return friendsService.create(newFriend);
    }

    @Get("/friends")
    public FriendsData list() {
        return friendsService.list();
    }
}
