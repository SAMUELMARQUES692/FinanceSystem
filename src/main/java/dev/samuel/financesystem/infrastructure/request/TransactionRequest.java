package dev.samuel.financesystem.infrastructure.request;

import dev.samuel.financesystem.core.enums.Type;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequest(

        @NotNull
        Long destinationId,

        @NotNull
        @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
        BigDecimal amount,

        @NotNull
        Type type,

        String description
) {
}
