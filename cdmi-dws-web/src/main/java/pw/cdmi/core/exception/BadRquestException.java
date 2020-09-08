package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class BadRquestException extends BaseRunException {
    private static final long serialVersionUID = 5928641424976905468L;

    public BadRquestException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getMessage());
    }

    public BadRquestException(String excepMessage) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getMessage(),
                excepMessage);
    }

    public BadRquestException(String excption, String msg) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.getCode(), excption, msg);
    }

    public BadRquestException(Throwable e) {
        super(e, HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getMessage());
    }
}
