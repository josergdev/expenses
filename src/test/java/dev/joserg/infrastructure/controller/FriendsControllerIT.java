package dev.joserg.infrastructure.controller;

import dev.joserg.application.friend.data.NewFriendData;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@MicronautTest
public class FriendsControllerIT {

    @Test
    void it_should_add_new_friend(RequestSpecification spec) {
        spec
            .given()
                .contentType(ContentType.JSON)
                .body(new NewFriendData("someFriendName"))
            .when()
                .post("/friends")
            .then()
                .statusCode(HttpStatus.CREATED.getCode())
                .body("id", not(emptyOrNullString()))
                .body("name", is("someFriendName"));
    }
}
