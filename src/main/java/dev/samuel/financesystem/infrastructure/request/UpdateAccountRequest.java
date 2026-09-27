package dev.samuel.financesystem.infrastructure.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateAccountRequest(

        @NotBlank
        @Size(max = 50)
        String pix

) {}
