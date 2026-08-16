package dev.samuel.financesystem.infrastructure.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record UserRequest(

     @NotBlank
     String name,

    @NotBlank
    String email,

    @NotBlank
    String password,

    @NotEmpty
    @NotNull
    List<Long> scopes


) {
}
