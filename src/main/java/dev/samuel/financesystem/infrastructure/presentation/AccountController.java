package dev.samuel.financesystem.infrastructure.presentation;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.entities.User;
import dev.samuel.financesystem.core.gateway.AccountGateway;
import dev.samuel.financesystem.core.gateway.UserGateway;
import dev.samuel.financesystem.core.usecases.account.createAccount.CreateAccountUseCase;
import dev.samuel.financesystem.core.usecases.account.findAccount.FindAccountByUserIdUseCase;
import dev.samuel.financesystem.core.usecases.account.findAccountById.FindByIdUseCase;
import dev.samuel.financesystem.core.usecases.account.findAccountByPix.FindAccountByPixUseCase;
import dev.samuel.financesystem.core.usecases.account.updateAccount.UpdateAccountUseCase;
import dev.samuel.financesystem.infrastructure.mapper.AccountMapper;
import dev.samuel.financesystem.infrastructure.request.AccountRequest;
import dev.samuel.financesystem.infrastructure.request.UpdateAccountRequest;
import dev.samuel.financesystem.infrastructure.response.AccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final FindAccountByUserIdUseCase findAccountByUserIdUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final FindByIdUseCase findByIdUseCase;
    private final FindAccountByPixUseCase findAccountByPixUseCase;
    private final AccountMapper accountMapper;
    private final AccountGateway accountGateway;
    private final UserGateway userGateway;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @RequestBody @Valid AccountRequest request,
            JwtAuthenticationToken token) {

        Long userId = Long.parseLong(token.getName()); // token.getName() retorna o subject
        User user = userGateway.findUserById(userId);

        Account newAccount = Account.builder()
                .id(null)
                .user(user)
                .balance(request.balance())
                .agency(request.agency())
                .number(request.number())
                .pix(request.pix())
                .createdAt(null)
                .build();

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
        userGateway.findUserById(userId);

        Account updateAccount = Account.builder()
                .pix(request.pix())
                .build();

        Account update = updateAccountUseCase.execute(id, updateAccount);
        AccountResponse response = accountMapper.toAccountResponse(update);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accountMapper.toAccountResponse(findByIdUseCase.execute(id)));
    }

    @GetMapping("/pix/{pix}")
    public ResponseEntity<AccountResponse> findByPix(@PathVariable String pix) {
        return ResponseEntity.ok(accountMapper.toAccountResponse(findAccountByPixUseCase.execute(pix)));
    }

}
