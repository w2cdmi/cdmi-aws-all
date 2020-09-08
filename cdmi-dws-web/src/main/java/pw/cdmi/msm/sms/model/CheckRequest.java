package pw.cdmi.msm.sms.model;

import lombok.Data;

@Data
public class CheckRequest {
	private String mobile;
	private String code;
}
