package dev.samuel.financesystem.infrastructure.documentation;

import dev.samuel.financesystem.infrastructure.request.AccountRequest;
import dev.samuel.financesystem.infrastructure.request.UpdateAccountRequest;
import dev.samuel.financesystem.infrastructure.response.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Account Controller", description = "Endpoints for managing accounts")
public interface AccountControllerDoc {

    @Operation(summary = "Create Account", description = "Metodo responsavel por criar uma nova conta para o usuario logado")
    @ApiResponse(responseCode = "201", description = "Conta criada com sucesso", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    @ApiResponse(responseCode = "400", description = "Conta não cadastrada", content = @Content())
    ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountRequest request, JwtAuthenticationToken token);

    @Operation(summary = "Get Balance", description = "Metodo responsavel por buscar o saldo da conta do usuario logado")
    @ApiResponse(responseCode = "200", description = "Saldo Retornado com sucesso", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content())
    ResponseEntity<AccountResponse> getBalance(JwtAuthenticationToken token);

    @Operation(summary = "Find User ID", description = "Metodo responsavel por buscar a conta que pertence ao usuario logado")
    @ApiResponse(responseCode = "200", description = "Conta do usuario encontrada", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    @ApiResponse(responseCode = "404", description = "Conta do usuario logado não encontrada", content = @Content())
    ResponseEntity<AccountResponse> findByuserId(JwtAuthenticationToken token);

    @Operation(summary = "Update Account", description = "Metodo responsavel por atualizar a conta do usuario logado")
    @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content())
    @ApiResponse(responseCode = "400", description = "Campos obrigatorios não foram preechidos", content = @Content())
    ResponseEntity<AccountResponse> updateAccount(@PathVariable Long id, @RequestBody @Valid UpdateAccountRequest request, JwtAuthenticationToken token);

    @Operation(summary = "Find By ID", description = "Metodo responsavel por buscar a conta do usuario logado pelo id")
    @ApiResponse(responseCode = "200", description = "Conta do usuario encontrada", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content())
    ResponseEntity<AccountResponse> findById(@PathVariable Long id);

    @Operation(summary = "Find By PIX", description = "Metodo responsavel por buscar a conta do usuario logado pelo pix")
    @ApiResponse(responseCode = "200", description = "Conta do usuario encontrada", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content())
    ResponseEntity<AccountResponse> findByPix(@PathVariable String pix);

}
