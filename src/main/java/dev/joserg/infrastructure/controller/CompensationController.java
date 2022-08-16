package dev.joserg.infrastructure.controller;

import dev.joserg.application.compensation.CompensationService;
import dev.joserg.application.compensation.data.CompensationData;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller
public class CompensationController {

    @Inject
    CompensationService compensationService;

    @Get("/compensation")
    public CompensationData compensation() {
        return compensationService.compensation();
    }
}
