package dev.samuel.financesystem.infrastructure.presentation;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.entities.Transaction;
import dev.samuel.financesystem.core.enums.Status;
import dev.samuel.financesystem.core.enums.Type;
import dev.samuel.financesystem.core.gateway.AccountGateway;
import dev.samuel.financesystem.core.usecases.transactions.reportUse.ReportUseCase;
import dev.samuel.financesystem.core.usecases.transactions.transferByPixUseCase.TransferByPixUseCase;
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

    private final TransferByPixUseCase transferUseCase;
    private final TransactionMapper transactionMapper;
    private final AccountGateway accountGateway;
    private final ReportUseCase reportUseCase;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(
            @RequestBody @Valid TransactionRequest request,
            JwtAuthenticationToken token) {

        Long userId = Long.parseLong(token.getName());
        Account originAccount = accountGateway.findByUserId(userId); // <- busca conta pelo userId
        Account destinationAccount = accountGateway.findByPix(request.pix());

        Transaction transaction = Transaction.builder()
                .origin(originAccount)
                .destination(destinationAccount)
                .amount(request.amount())
                .type(Type.TRANSFER)
                .status(Status.PENDING)
                .description(request.description())
                .build();

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
