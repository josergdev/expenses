package dev.joserg.infrastructure.controller;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

@MicronautTest
public class HealthControllerIT {

    @Test
    void it_should_return_status_ok(RequestSpecification spec) {
        spec.
                when()
                    .get("/health")
                .then()
                    .body("status", equalTo("UP"));
    }
}
