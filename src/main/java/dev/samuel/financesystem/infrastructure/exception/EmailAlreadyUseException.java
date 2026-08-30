package dev.samuel.financesystem.infrastructure.exception;

public class EmailAlreadyUseException extends RuntimeException{
    public EmailAlreadyUseException(String message) {
        super(message);
    }
}
