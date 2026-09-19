package dev.samuel.financesystem.infrastructure.presentation;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.entities.Transaction;
import dev.samuel.financesystem.core.gateway.AccountGateway;
import dev.samuel.financesystem.core.usecases.createAccount.CreateAccountUseCase;
import dev.samuel.financesystem.core.usecases.findAccount.FindAccountByUserIdUseCase;
import dev.samuel.financesystem.core.usecases.reportUse.ReportUseCase;
import dev.samuel.financesystem.core.usecases.updateAccount.UpdateAccountUseCase;
import dev.samuel.financesystem.infrastructure.mapper.AccountMapper;
import dev.samuel.financesystem.infrastructure.mapper.TransactionMapper;
import dev.samuel.financesystem.infrastructure.request.AccountRequest;
import dev.samuel.financesystem.infrastructure.request.UpdateAccountRequest;
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
    private final FindAccountByUserIdUseCase findAccountByUserIdUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final AccountMapper accountMapper;
    private final AccountGateway accountGateway;

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
                request.pix(),
                null            // createdAt
        );

        Account createdAccount = createAccountUseCase.execute(newAccount);
        AccountResponse response = accountMapper.toAccountResponse(createdAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<AccountResponse> getBalance(JwtAuthenticationToken token) {
        Long userId = Long.parseLong(token.getName());
        Account account = accountGateway.getBalance(userId);
        return ResponseEntity.ok(accountMapper.toAccountResponse(account));
    }

    @GetMapping("/user")
    public ResponseEntity<AccountResponse> findByuserId(JwtAuthenticationToken token) {
        Long userId = Long.parseLong(token.getName());
        Account account = findAccountByUserIdUseCase.execute(userId);
        return ResponseEntity.ok(accountMapper.toAccountResponse(account));
    }

    @PutMapping("{id}")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAccountRequest request,
            JwtAuthenticationToken token) {

        Long userId = Long.parseLong(token.getName());

        Account updateAccount = new Account(
                null,           // id
                userId,         // userId
                null,           // balance
                null,
                null,
                request.pix(),
                null            // createdAt
        );

        Account update = updateAccountUseCase.execute(id, updateAccount);
        AccountResponse response = accountMapper.toAccountResponse(update);
        return ResponseEntity.ok(response);
    }

}
