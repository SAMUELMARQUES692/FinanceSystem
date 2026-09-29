package dev.samuel.financesystem.infrastructure.documentation;

import dev.samuel.financesystem.infrastructure.request.UserRequest;
import dev.samuel.financesystem.infrastructure.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "User Controller", description = "Endpoints for managing users")
public interface UserControllerDoc {

    @Operation(summary = "Create User", description = "Metodo responsavel por criar novos usuarios")
    @ApiResponse(responseCode = "201", description = "Usuario criada com sucesso", content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "400", description = "Usuario não cadastrada", content = @Content())
    ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request);

    @Operation(summary = "Update User", description = "Metodo responsavel por atualizar um usuario existente")
    @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso", content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "400", description = "Usuario não atualizado", content = @Content())
    @ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = @Content())
    ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest request);

    @Operation(summary = "Delete User", description = "Metodo responsavel por deletar um usuario existente")
    @ApiResponse(responseCode = "204", description = "Usuario deletado com sucesso", content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = @Content())
    ResponseEntity<Void> deleteUser(@PathVariable Long id);

    @Operation(summary = "Find Email", description = "Metodo responsavel por buscar um usuario existente pelo email")
    @ApiResponse(responseCode = "200", description = "Usuario encotrado com sucesso", content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = @Content())
    ResponseEntity<UserResponse> findByEmail(@PathVariable String email);

    @Operation(summary = "Find All Users", description = "Metodo responsavel por buscar todos os usuarios cadastrados no sistema")
    @ApiResponse(responseCode = "200", description = "Usuarios encotrados com sucesso", content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "404", description = "Usuarios não encontrados", content = @Content())
    ResponseEntity<List<UserResponse>> findAllUsers();
}
