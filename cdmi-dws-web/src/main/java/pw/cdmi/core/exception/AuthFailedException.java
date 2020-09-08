package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class AuthFailedException extends BaseRunException {
    private static final long serialVersionUID = -7955677673261964702L;

    public AuthFailedException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.TOKEN_UNAUTHORIZED.getCode(), ErrorCode.TOKEN_UNAUTHORIZED.getMessage());
    }

    public AuthFailedException(String excepMessage) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.TOKEN_UNAUTHORIZED.getCode(), ErrorCode.TOKEN_UNAUTHORIZED.getMessage(), excepMessage);
    }

    public AuthFailedException(Throwable e) {
        super(e, HttpStatus.UNAUTHORIZED, ErrorCode.TOKEN_UNAUTHORIZED.getCode(), ErrorCode.TOKEN_UNAUTHORIZED.getMessage());
    }

}
