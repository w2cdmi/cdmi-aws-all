package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class NoThumbNailException extends BaseRunException {

    private static final long serialVersionUID = 2837358554580124783L;

    public NoThumbNailException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.NO_SUCH_RESOUCE.getCode(),
                ErrorCode.NO_SUCH_RESOUCE.getMessage());
    }

    public NoThumbNailException(String excepMessage) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NO_SUCH_RESOUCE.getCode(),
                ErrorCode.NO_SUCH_RESOUCE.getMessage(), excepMessage);
    }
}
