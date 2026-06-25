package dev.samuel.financesystem.infrastructure.handler;

import dev.samuel.financesystem.infrastructure.exception.*;
import dev.samuel.financesystem.infrastructure.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserOrPasswordIncorectException.class)
    public ResponseEntity<ErrorResponse> userAndPasswordIncorect(UserOrPasswordIncorectException exception) {
        ErrorResponse error = new ErrorResponse(
                "BAD_CREDENTIAL",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> duplicatedUser(AccountAlreadyExistsException exception) {
        ErrorResponse error = new ErrorResponse(
                "DUPLICATED_USER",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(DestinationAccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> destinationNotFound(DestinationAccountNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                "DESTINATION_NOT_FOUND",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(OriginAccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> originNotFound(OriginAccountNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                "ORIGIN_NOT_FOUND",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> insufficientBalance(InsufficientBalanceException exception) {
        ErrorResponse error = new ErrorResponse(
                "INSUFFICIENT_BALANCE",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(SameAccountException.class)
    public ResponseEntity<ErrorResponse> sameAccount(SameAccountException exception) {
        ErrorResponse error = new ErrorResponse(
                "SAME_ACCOUNT",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Fallback para erros inesperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Unexpected error: ", ex); // adicione isso
        ErrorResponse error = new ErrorResponse(
                "INTERNAL_ERROR",
                "An unexpected error occurred",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
