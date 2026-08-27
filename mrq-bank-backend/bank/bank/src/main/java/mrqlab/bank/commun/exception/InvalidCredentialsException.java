package mrqlab.bank.commun.exception;

public class InvalidCredentialsException extends RuntimeException {
    private final String response;

    public InvalidCredentialsException(String response){
        this.response = response;
    }
}
