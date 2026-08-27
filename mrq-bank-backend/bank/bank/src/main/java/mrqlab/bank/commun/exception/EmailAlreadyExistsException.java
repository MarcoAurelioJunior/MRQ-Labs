package mrqlab.bank.commun.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    private final String response;
    public EmailAlreadyExistsException(String response) {
        this.response = response;
    }
}
