package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class NoSuchOptionException extends BaseRunException {
    private static final long serialVersionUID = -5895344180541247341L;

    public NoSuchOptionException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.NO_SUCH_OPTION.getCode(),
                ErrorCode.NO_SUCH_OPTION.getMessage());
    }

    public NoSuchOptionException(Throwable e) {
        super(e, HttpStatus.BAD_REQUEST, ErrorCode.NO_SUCH_OPTION.getCode(),
                ErrorCode.NO_SUCH_OPTION.getMessage());
    }

    public NoSuchOptionException(String excepMessage) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.NO_SUCH_OPTION.getCode(),
                ErrorCode.NO_SUCH_OPTION.getMessage(), excepMessage);
    }
}