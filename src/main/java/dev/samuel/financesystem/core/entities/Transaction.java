package dev.samuel.financesystem.core.entities;

import dev.samuel.financesystem.core.enums.Status;
import dev.samuel.financesystem.core.enums.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        Long id,
        Long originId,
        Long destinationId,
        BigDecimal amount,
        Type type,
        Status status,
        String description,
        LocalDateTime createdAt


) {
}
