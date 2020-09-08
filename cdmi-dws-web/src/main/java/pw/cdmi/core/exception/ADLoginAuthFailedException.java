package pw.cdmi.core.exception;

public class ADLoginAuthFailedException extends AuthFailedException {
    /**
     *
     */
    private static final long serialVersionUID = 1415701433937878211L;

    public ADLoginAuthFailedException() {
        super();
    }

    public ADLoginAuthFailedException(String message) {
        super(message);
    }

    public ADLoginAuthFailedException(Throwable cause) {
        super(cause);
    }
}
