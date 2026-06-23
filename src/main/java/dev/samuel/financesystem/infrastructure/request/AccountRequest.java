package dev.samuel.financesystem.infrastructure.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AccountRequest(

        @NotNull
        @DecimalMin(value = "0.00", message = "Balance must be greater than or equal to 0")
        BigDecimal balance,

        @NotBlank
        @Size(max = 10)
        String agency,

        @NotBlank
        @Size(max = 20)
        String number


) {
}
