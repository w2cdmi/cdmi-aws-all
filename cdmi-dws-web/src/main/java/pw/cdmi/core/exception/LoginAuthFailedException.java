package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class LoginAuthFailedException extends BaseRunNoStackException {
    private static final long serialVersionUID = 1325907017686953441L;

    public LoginAuthFailedException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.LOGIN_UNAUTHORIZED.getCode(), ErrorCode.LOGIN_UNAUTHORIZED.getMessage());
    }

    public LoginAuthFailedException(String excepMessage) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.LOGIN_UNAUTHORIZED.getCode(), ErrorCode.LOGIN_UNAUTHORIZED.getMessage(), excepMessage);
    }
}
