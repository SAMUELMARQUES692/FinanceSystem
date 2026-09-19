package dev.samuel.financesystem.infrastructure.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateAccountRequest(

        @NotBlank
        @Size(max = 50)
        String pix

) {}
