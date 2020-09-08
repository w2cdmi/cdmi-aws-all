package pw.cdmi.core.http.exception;

import pw.cdmi.error.ErrorReason;

public class AWSServiceException extends AWSException {
	private static final long serialVersionUID = 7586638404065538007L;
	
	
    public AWSServiceException(ErrorReason reason) {
    	super(reason);
    }

}
