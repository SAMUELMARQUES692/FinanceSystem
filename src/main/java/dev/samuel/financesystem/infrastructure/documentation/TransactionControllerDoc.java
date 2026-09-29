package dev.samuel.financesystem.infrastructure.documentation;

import dev.samuel.financesystem.infrastructure.request.TransactionRequest;
import dev.samuel.financesystem.infrastructure.response.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Transaction Controller", description = "Endpoints for managing transactions")
public interface TransactionControllerDoc {

    @Operation(summary = "Transfer", description = "Metodo responsavel por fazer transferencia entre contas do usuario logado e outro usuario usando a chave pix")
    @ApiResponse(responseCode = "200", description = "Transferencia efetuada com sucesso", content = @Content(schema = @Schema(implementation = TransactionResponse.class)))
    @ApiResponse(responseCode = "400", description = "Alguma informação passada esta incorreta", content = @Content())
    @ApiResponse(responseCode = "404", description = "Conta Destinataria não encontrada", content = @Content())
    ResponseEntity<TransactionResponse> transfer(@RequestBody @Valid TransactionRequest request, JwtAuthenticationToken token);

    @Operation(summary = "Get Report", description = "Metodo responsavel por gerar um extrado de saidas e entradas da conta do usuario logado")
    @ApiResponse(responseCode = "200", description = "relatorio gerado com sucesso", content = @Content(schema = @Schema(implementation = TransactionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content())
    ResponseEntity<List<TransactionResponse>> getReport(JwtAuthenticationToken token);
}
