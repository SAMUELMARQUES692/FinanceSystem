package dev.samuel.financesystem.infrastructure.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.samuel.financesystem.core.enums.Status;
import dev.samuel.financesystem.core.enums.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        Long originId,
        Long destinationId,
        BigDecimal amount,
        Type type,
        Status status,
        String description,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt


) {
}
