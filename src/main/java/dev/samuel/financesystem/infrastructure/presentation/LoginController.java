package dev.samuel.financesystem.infrastructure.presentation;

import dev.samuel.financesystem.core.usecases.login.LoginInput;
import dev.samuel.financesystem.core.usecases.login.LoginOutput;
import dev.samuel.financesystem.core.usecases.login.LoginUseCase;
import dev.samuel.financesystem.infrastructure.mapper.UserMapper;
import dev.samuel.financesystem.infrastructure.request.LoginRequest;
import dev.samuel.financesystem.infrastructure.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginUseCase loginUseCase;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginInput input = new LoginInput(request.email(), request.password());
        LoginOutput output = loginUseCase.execute(input);
        return ResponseEntity.ok(
                LoginResponse.builder()
                        .accessToken(output.accessToken())
                        .tokenType(output.tokenType())
                        .expiresIn(output.expiresIn())
                        .build()
        );
    }
}
