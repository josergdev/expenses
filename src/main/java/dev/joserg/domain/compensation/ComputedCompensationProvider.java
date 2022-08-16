package dev.joserg.domain.compensation;

import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class ComputedCompensationProvider implements CompensationProvider {
    @Override
    public Compensation compensation() {
        return new Compensation(List.of());
    }
}
