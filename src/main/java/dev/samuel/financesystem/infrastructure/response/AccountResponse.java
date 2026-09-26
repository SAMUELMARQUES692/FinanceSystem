package dev.samuel.financesystem.infrastructure.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String userName,
        String userCpf,
        BigDecimal balance,
        String agency,
        String number,
        String pix,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt


) {
}
