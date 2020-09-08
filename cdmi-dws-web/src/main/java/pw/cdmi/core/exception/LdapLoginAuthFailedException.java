package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class LdapLoginAuthFailedException extends BaseRunNoStackException {
    private static final long serialVersionUID = 8582649755372504312L;

    public LdapLoginAuthFailedException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.LOGIN_UNAUTHORIZED.getCode(), "AD authentication fails, the user name or password is incorrect.");
    }
}
