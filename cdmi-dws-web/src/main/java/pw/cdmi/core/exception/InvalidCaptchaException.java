package pw.cdmi.core.exception;

public class InvalidCaptchaException extends AuthFailedException {
    private static final long serialVersionUID = -8385462547381801475L;

    public InvalidCaptchaException() {
        super();
    }

    public InvalidCaptchaException(String message) {
        super(message);
    }

    public InvalidCaptchaException(Throwable cause) {
        super(cause);
    }
}
