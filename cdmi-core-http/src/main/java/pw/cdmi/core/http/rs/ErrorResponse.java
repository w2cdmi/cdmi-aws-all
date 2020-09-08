package pw.cdmi.core.http.rs;

import lombok.Data;

@Data
public class ErrorResponse {
	private String code;				//错误码
	private String message;				//错误消息
	private String cause;				//调试时出现，导致问题的原因
	private String resource;			//调试时出现
	private String requestId;			//调试时出现
	private String hostId;				//调试时出现
}
