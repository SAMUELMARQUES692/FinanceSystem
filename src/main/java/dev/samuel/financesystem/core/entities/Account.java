package dev.samuel.financesystem.core.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Account(
        Long id,
        Long userId,
        BigDecimal balance,
        String agency,
        String number,
        LocalDateTime createdAt
) implements Serializable {}
