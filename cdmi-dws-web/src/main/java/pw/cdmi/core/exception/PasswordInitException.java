package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class PasswordInitException extends BaseRunException {
    private static final long serialVersionUID = 3792468704127719114L;

    public PasswordInitException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.NEED_CHANGE_PASSWORD.getCode(), ErrorCode.NEED_CHANGE_PASSWORD.getMessage());
    }

    public PasswordInitException(String msg) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.NEED_CHANGE_PASSWORD.getCode(), ErrorCode.NEED_CHANGE_PASSWORD.getMessage(), msg);
    }

    public PasswordInitException(Throwable e) {
        super(e, HttpStatus.UNAUTHORIZED, ErrorCode.NEED_CHANGE_PASSWORD.getCode(), ErrorCode.NEED_CHANGE_PASSWORD.getMessage());
    }
}
