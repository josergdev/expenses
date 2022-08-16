package dev.joserg.application.compensation;

import dev.joserg.application.compensation.data.CompensationData;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class CompensationService {
    public CompensationData compensation() {
        return new CompensationData(List.of());
    }
}
