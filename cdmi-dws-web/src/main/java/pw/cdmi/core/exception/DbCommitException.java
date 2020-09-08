package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class DbCommitException extends BaseRunException {
    private static final long serialVersionUID = 4200186677249802080L;

    public DbCommitException() {
        super(HttpStatus.EXPECTATION_FAILED, "TransactionCommitError",
                ErrorCode.DB_SUBMIT_EXCEPTION.toString());
    }

    public DbCommitException(String excepMessage) {
        super(HttpStatus.EXPECTATION_FAILED, "TransactionCommitError",
                ErrorCode.DB_SUBMIT_EXCEPTION.toString(), excepMessage);
    }

}
