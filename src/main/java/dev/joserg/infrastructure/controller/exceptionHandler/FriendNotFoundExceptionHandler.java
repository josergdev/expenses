package dev.joserg.infrastructure.controller.exceptionHandler;

import dev.joserg.application.friend.exception.FriendNotFoundException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

import java.text.MessageFormat;
import java.util.Map;

@Singleton
public class FriendNotFoundExceptionHandler implements ExceptionHandler<FriendNotFoundException, HttpResponse<Map<String, Object>>> {
    @Override
    public HttpResponse<Map<String, Object>> handle(HttpRequest request, FriendNotFoundException exception) {
        return HttpResponse.notFound(
                Map.of("message", MessageFormat.format("Friend with id <{0}> not found.", exception.friendId()))
        );
    }
}
