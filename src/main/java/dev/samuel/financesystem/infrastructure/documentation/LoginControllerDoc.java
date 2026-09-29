package dev.samuel.financesystem.infrastructure.documentation;

import dev.samuel.financesystem.infrastructure.request.LoginRequest;
import dev.samuel.financesystem.infrastructure.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Login Controller", description = "Endpoints for user login and authentication")
public interface LoginControllerDoc {

    @Operation(summary = "Login User", description = "Metodo responsavel por verificar o usuario corretamente e fazer login na conta")
    @ApiResponse(responseCode = "200", description = "Usuario logado com sucesso", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "400", description = "Algum campo obrigatorio não preenchido", content = @Content())
    @ApiResponse(responseCode = "401", description = "Email ou senha estão incorretos", content = @Content())
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);
}
