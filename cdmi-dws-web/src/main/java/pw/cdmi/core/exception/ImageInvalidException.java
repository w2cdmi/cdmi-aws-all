package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class ImageInvalidException extends BaseRunException {

    private static final long serialVersionUID = -7025256617754421196L;

    public ImageInvalidException() {
        super(HttpStatus.CONFLICT, ErrorCode.FILES_CONFLICT.getCode(), ErrorCode.FILES_CONFLICT.getMessage());
    }

    public ImageInvalidException(Throwable e) {
        super(e, HttpStatus.CONFLICT, ErrorCode.FILES_CONFLICT.getCode(),
                ErrorCode.FILES_CONFLICT.getMessage());
    }

    public ImageInvalidException(String excepMessage) {
        super(HttpStatus.CONFLICT, ErrorCode.FILES_CONFLICT.getCode(), ErrorCode.FILES_CONFLICT.getMessage(),
                excepMessage);
    }
}
