package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class LinkApprovingException extends BaseRunException {
    public LinkApprovingException() {
        super(HttpStatus.FORBIDDEN, ErrorCode.LINK_APPROVING.getCode(), ErrorCode.LINK_APPROVING.getMessage());
    }

    public LinkApprovingException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.LINK_APPROVING.getCode(), ErrorCode.LINK_APPROVING.getMessage(), message);
    }
}
