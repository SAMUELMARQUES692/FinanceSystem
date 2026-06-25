package dev.samuel.financesystem.infrastructure.exception;

public class DestinationAccountNotFoundException extends RuntimeException{
    public DestinationAccountNotFoundException(String message) {
        super(message);

    }
}
