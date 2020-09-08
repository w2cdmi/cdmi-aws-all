package pw.cdmi.msm.activity.rs;

import javax.persistence.Column;

import lombok.Data;

@Data
public class ApplicationRequest {
	
	
	private String activityId;
	
	private String userId;
	
	private Boolean isCost;		//是否交费  
}
