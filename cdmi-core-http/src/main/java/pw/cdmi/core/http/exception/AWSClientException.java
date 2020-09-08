package pw.cdmi.core.http.exception;

import pw.cdmi.error.ErrorMessage;
import pw.cdmi.error.ErrorReason;

public class AWSClientException extends AWSException {
	private static final long serialVersionUID = 6413756326321098091L;
    
    public AWSClientException(ErrorMessage message, ErrorReason reason) {
        super(message, reason);
    }
    
    public AWSClientException(ErrorMessage message, ErrorReason reason, String... Parameters) {
        super(message, reason);
    }
    
}
