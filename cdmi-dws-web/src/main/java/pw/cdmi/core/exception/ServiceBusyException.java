package pw.cdmi.core.exception;

import org.springframework.http.HttpStatus;

public class ServiceBusyException extends BaseRunException {

    private static final long serialVersionUID = -5288322319268320453L;

    public ServiceBusyException() {
        super(HttpStatus.CONFLICT, ErrorCode.SERVICE_BUSY.getCode(), ErrorCode.SERVICE_BUSY.getMessage());
    }

    public ServiceBusyException(String excepMessage) {
        super(HttpStatus.CONFLICT, ErrorCode.SERVICE_BUSY.getCode(), ErrorCode.SERVICE_BUSY.getMessage(),
                excepMessage);
    }

    public ServiceBusyException(Throwable e) {

        super(e, HttpStatus.CONFLICT, ErrorCode.SERVICE_BUSY.getCode(), ErrorCode.SERVICE_BUSY.getMessage());

    }
}
