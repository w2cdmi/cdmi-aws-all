package pw.cdmi.msm.activity.model;

import lombok.Data;

@Data
public class Belong {
	private String appId; // 数据归属应用ID
	
	private String siteId; // 对应的平台内子站点Id，这个子站点可能是租户，可以是频道

	private String tenantId; // 对应的租户ID
	

}
