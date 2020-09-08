package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class NoSuchParentException extends BaseRunException {
    private static final long serialVersionUID = -9103267694283191535L;

    public NoSuchParentException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.NO_SUCH_PARENT.getCode(), ErrorCode.NO_SUCH_PARENT.getMessage());
    }

    public NoSuchParentException(String excepMessage) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NO_SUCH_PARENT.getCode(),
                ErrorCode.NO_SUCH_PARENT.getMessage(), excepMessage);
    }

}
