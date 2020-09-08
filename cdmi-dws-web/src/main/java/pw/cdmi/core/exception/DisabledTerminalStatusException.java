package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class DisabledTerminalStatusException extends BaseRunException {
    private static final long serialVersionUID = -2147093566523637264L;

    public DisabledTerminalStatusException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.CLIENT_UNAUTHORIZED.getCode(), ErrorCode.CLIENT_UNAUTHORIZED.getMessage());
    }

    public DisabledTerminalStatusException(String excepMessage) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.CLIENT_UNAUTHORIZED.getCode(), ErrorCode.CLIENT_UNAUTHORIZED.getMessage(), excepMessage);
    }
}
