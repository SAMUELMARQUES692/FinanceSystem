package dev.samuel.financesystem.infrastructure.presentation;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.entities.Transaction;
import dev.samuel.financesystem.core.usecases.createAccount.CreateAccountUseCase;
import dev.samuel.financesystem.core.usecases.reportUse.ReportUseCase;
import dev.samuel.financesystem.infrastructure.mapper.AccountMapper;
import dev.samuel.financesystem.infrastructure.mapper.TransactionMapper;
import dev.samuel.financesystem.infrastructure.request.AccountRequest;
import dev.samuel.financesystem.infrastructure.response.AccountResponse;
import dev.samuel.financesystem.infrastructure.response.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final AccountMapper accountMapper;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @RequestBody @Valid AccountRequest request,
            JwtAuthenticationToken token) {

        Long userId = Long.parseLong(token.getName()); // token.getName() retorna o subject

        Account newAccount = new Account(
                null,           // id
                userId,         // userId
                request.balance(),
                request.agency(),
                request.number(),
                null            // createdAt
        );

        Account createdAccount = createAccountUseCase.execute(newAccount);
        AccountResponse response = accountMapper.toAccountResponse(createdAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
