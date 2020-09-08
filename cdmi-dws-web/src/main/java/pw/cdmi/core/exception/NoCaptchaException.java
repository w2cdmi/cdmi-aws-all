package pw.cdmi.core.exception;

public class NoCaptchaException extends AuthFailedException {
    private static final long serialVersionUID = -8385462547381801475L;

    public NoCaptchaException() {
        super();
    }

    public NoCaptchaException(String message) {
        super(message);
    }

    public NoCaptchaException(Throwable cause) {
        super(cause);
    }
}
