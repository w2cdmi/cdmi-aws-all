package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class DbRollbackException extends BaseRunException {

    private static final long serialVersionUID = -4564360586972221238L;

    public DbRollbackException() {
        super(HttpStatus.EXPECTATION_FAILED, "TransactionRollbackError",
                ErrorCode.DB_ROLL_BACK_EXCEPTION.toString());
    }

    public DbRollbackException(String excepMessage) {
        super(HttpStatus.EXPECTATION_FAILED, "TransactionRollbackError",
                ErrorCode.DB_ROLL_BACK_EXCEPTION.toString(), excepMessage);
    }

}
