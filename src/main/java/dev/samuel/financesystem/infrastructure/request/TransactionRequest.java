package dev.samuel.financesystem.infrastructure.request;

import dev.samuel.financesystem.core.enums.Type;
import dev.samuel.financesystem.infrastructure.persistence.Account;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionRequest(

        @NotNull
        String pix,

        @NotNull
        @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
        BigDecimal amount,

        @NotNull
        Type type,

        String description
) {
}
