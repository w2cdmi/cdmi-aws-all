package pw.cdmi.msm.activity.rs;

import pw.cdmi.msm.activity.model.ParticipateTypeSupper;
import lombok.Data;

@Data
public class ParticipateRequest {

	private String activityId;
	
	private String userId;
	
	private ParticipateTypeSupper type; 
}
