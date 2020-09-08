package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class DisabledUserApiException extends BaseRunException {
    private static final long serialVersionUID = -8400573900875171094L;

    public DisabledUserApiException() {
        super(HttpStatus.FORBIDDEN, ErrorCode.USER_DISABLED.getCode(), ErrorCode.USER_DISABLED.getMessage());
    }
}
