package pw.cdmi.core.entities;

import lombok.Data;

@Data
public abstract class MultiAppSiteTenancyUserModel {
	private String appId;
	
	private String siteId;
	
	private String tenantId;
	
	private String userId;
}
