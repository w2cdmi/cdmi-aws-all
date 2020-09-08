package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class NoSuchClientException extends BaseRunException {

    private static final long serialVersionUID = 9017004522973527109L;

    public NoSuchClientException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.NO_SUCH_CLIENT.getCode(), ErrorCode.NO_SUCH_CLIENT.getMessage());
    }

    public NoSuchClientException(String excepMessage) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NO_SUCH_CLIENT.getCode(),
                ErrorCode.NO_SUCH_CLIENT.getMessage(), excepMessage);
    }

    public NoSuchClientException(Throwable e) {
        super(e, HttpStatus.NOT_FOUND, ErrorCode.NO_SUCH_CLIENT.getCode(),
                ErrorCode.NO_SUCH_CLIENT.getMessage());
    }
}
