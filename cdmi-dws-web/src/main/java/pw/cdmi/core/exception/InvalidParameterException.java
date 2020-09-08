package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class InvalidParameterException extends BaseRunException {
    private static final long serialVersionUID = -7030998098122795455L;

    public InvalidParameterException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_PARAMETER.getCode(), ErrorCode.INVALID_PARAMETER.getMessage());
    }

    public InvalidParameterException(String excepMessage) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_PARAMETER.getCode(), ErrorCode.INVALID_PARAMETER.getMessage(), excepMessage);
    }

}
