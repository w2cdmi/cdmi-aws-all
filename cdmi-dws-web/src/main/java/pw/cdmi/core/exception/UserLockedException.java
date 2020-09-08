package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class UserLockedException extends BaseRunException {
    private static final long serialVersionUID = -2147093566523637264L;

    public UserLockedException() {
        super(HttpStatus.FORBIDDEN, ErrorCode.USER_LOCKED.getCode(), ErrorCode.USER_LOCKED.getMessage());
    }

    public UserLockedException(String excepMessage) {
        super(HttpStatus.FORBIDDEN, ErrorCode.USER_LOCKED.getCode(), ErrorCode.USER_LOCKED.getMessage(), excepMessage);
    }

}
