package pw.cdmi.core.exception;

/**
 * @author z00352794
 */
public class BussinessException extends RuntimeException {

    private static final long serialVersionUID = 4681973790425395293L;

    public BussinessException() {
        super();
    }

    public BussinessException(String message) {
        super(message);
    }

    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BussinessException(Throwable cause) {
        super(cause);
    }

    protected BussinessException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
