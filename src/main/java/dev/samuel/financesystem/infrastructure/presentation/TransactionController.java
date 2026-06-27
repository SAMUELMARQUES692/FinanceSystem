package dev.samuel.financesystem.infrastructure.presentation;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.entities.Transaction;
import dev.samuel.financesystem.core.enums.Status;
import dev.samuel.financesystem.core.enums.Type;
import dev.samuel.financesystem.core.gateway.AccountGateway;
import dev.samuel.financesystem.core.usecases.reportUse.ReportUseCase;
import dev.samuel.financesystem.core.usecases.transferUse.TransferUseCase;
import dev.samuel.financesystem.infrastructure.mapper.TransactionMapper;
import dev.samuel.financesystem.infrastructure.request.TransactionRequest;
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
@RequestMapping("/transactions")
public class TransactionController {

    private final TransferUseCase transferUseCase;
    private final TransactionMapper transactionMapper;
    private final AccountGateway accountGateway;
    private final ReportUseCase reportUseCase;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(
            @RequestBody @Valid TransactionRequest request,
            JwtAuthenticationToken token) {

        Long userId = Long.parseLong(token.getName());
        Account originAccount = accountGateway.findByUserId(userId); // <- busca conta pelo userId

        dev.samuel.financesystem.core.entities.Transaction transaction =
                new dev.samuel.financesystem.core.entities.Transaction(
                        null,
                        originAccount.id(), // <- id da conta, não do usuário
                        request.destinationId(),
                        request.amount(),
                        Type.TRANSFER,
                        Status.PENDING,
                        request.description(),
                        null
                );

        dev.samuel.financesystem.core.entities.Transaction result = transferUseCase.execute(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionMapper.toTransactionResponse(result));
    }


   @GetMapping("/report")
   public ResponseEntity<List<TransactionResponse>> getReport(JwtAuthenticationToken token) {
        Long userId = Long.parseLong(token.getName());
        Account account = accountGateway.findByUserId(userId);

       List<Transaction> transactions = reportUseCase.execute(account.id());
       List<TransactionResponse> response = transactions.stream()
               .map(transactionMapper::toTransactionResponse)
               .toList();

       return ResponseEntity.ok(response);
   }

}
