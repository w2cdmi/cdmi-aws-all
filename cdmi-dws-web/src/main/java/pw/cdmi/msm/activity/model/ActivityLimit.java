package pw.cdmi.msm.activity.model;

import lombok.Data;

@Data
public class ActivityLimit {
	private Boolean isLimitNumber;		//是否开启人数限制
	
	private Integer limitNumber;		//限制人数
	
	private Boolean isActivityCost;		//活动是否收费
	
	private Integer activityCost;		//活动费用
		
	private Boolean	isApply;			//是否开启报名
		
	private Boolean	isApplyCost;		//是否有报名费用
	
	private Integer applyCost;			//报名费用
	
	
}
