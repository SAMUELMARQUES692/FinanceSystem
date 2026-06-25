package dev.samuel.financesystem.infrastructure.exception;

public class OriginAccountNotFoundException extends RuntimeException{
    public OriginAccountNotFoundException(String message) {
        super(message);
    }
}
