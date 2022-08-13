package dev.joserg.infrastructure.controller;

import dev.joserg.application.balance.BalanceService;
import dev.joserg.application.balance.data.BalanceData;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller
public class BalanceController {

    @Inject
    private BalanceService balanceService;

    @Get("/balance")
    public BalanceData balance() {
        return balanceService.balance();
    }
}
