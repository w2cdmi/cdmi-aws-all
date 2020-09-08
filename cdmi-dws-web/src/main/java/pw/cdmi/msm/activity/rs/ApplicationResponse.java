package pw.cdmi.msm.activity.rs;

import lombok.Data;

@Data
public class ApplicationResponse {
	private String userId;
	private String userType;
	private String headImageUrl; // 头像的头像
	
	private String name; // 用户名称
	
	private String degree; // 用户等级
	
	private String activityId;
	private String creatTime;
	private Boolean isCost;
}
