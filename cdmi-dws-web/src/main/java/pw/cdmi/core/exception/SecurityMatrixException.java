package pw.cdmi.core.exception;

public class SecurityMatrixException extends AuthFailedException {

    private static final long serialVersionUID = -1216559425084843515L;

    public SecurityMatrixException() {
        super();
    }

    public SecurityMatrixException(String message) {
        super(message);
    }

    public SecurityMatrixException(Throwable cause) {
        super(cause);
    }

}
