package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class ExceedTeamSpaceMaxNumException extends BaseRunException {
    private static final long serialVersionUID = 5661002307120468373L;

    public ExceedTeamSpaceMaxNumException() {
        super(HttpStatus.PRECONDITION_FAILED, ErrorCode.EXCEED_MAX_TEAMSPACE_NUM.getCode(),
                ErrorCode.EXCEED_MAX_TEAMSPACE_NUM.getMessage());
    }

    public ExceedTeamSpaceMaxNumException(String excepMessage) {
        super(HttpStatus.PRECONDITION_FAILED, ErrorCode.EXCEED_MAX_TEAMSPACE_NUM.getCode(),
                ErrorCode.EXCEED_MAX_TEAMSPACE_NUM.getMessage(), excepMessage);
    }
}
