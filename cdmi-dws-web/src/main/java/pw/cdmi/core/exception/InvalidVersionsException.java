package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class InvalidVersionsException extends BaseRunException {
    private static final long serialVersionUID = 3187889527609485428L;

    public InvalidVersionsException(String excepMessage, String code) {
        super(HttpStatus.NOT_FOUND, code, ErrorCode.NO_SUCH_VERSION.getMessage(), excepMessage);
    }
}
