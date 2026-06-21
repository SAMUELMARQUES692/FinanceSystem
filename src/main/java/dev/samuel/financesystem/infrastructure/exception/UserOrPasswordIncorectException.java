package dev.samuel.financesystem.infrastructure.exception;

public class UserOrPasswordIncorectException extends RuntimeException{
    public UserOrPasswordIncorectException(String email, String password) {
        super("Email or Password are incorrect");
    }
}
