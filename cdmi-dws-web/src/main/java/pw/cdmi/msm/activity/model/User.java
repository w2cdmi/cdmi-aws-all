package pw.cdmi.msm.activity.model;

import lombok.Data;

@Data
public class User {
	private String activityUid;		//用户平台id
	
	private String activityAid;		//用户应用id
	
	private	String activityTid;		//用户租户id
}
