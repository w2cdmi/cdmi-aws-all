package pw.cdmi.core.exception;

public class DisabledUserException extends AuthFailedException {
    public DisabledUserException() {
        super();
    }

    public DisabledUserException(String message) {
        super(message);
    }

    public DisabledUserException(Throwable cause) {
        super(cause);
    }
}
