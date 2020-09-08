package pw.cdmi.core.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"code", "requestID", "message"})
public class CommonResponseEntiy {

    private String code = "OK";

    private String message;

    private Object object;

    private String requestId;

    public CommonResponseEntiy(Object object, String requestID) {
        this(requestID);
        this.object = object;
    }

    public CommonResponseEntiy(String requestID) {
        this.requestId = requestID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
