package dev.samuel.financesystem.infrastructure.exception;

public class SameAccountException extends RuntimeException{
    public SameAccountException(String message) {
        super(message);
    }
}
