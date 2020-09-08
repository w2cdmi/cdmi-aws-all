package pw.cdmi.core.entities;

import lombok.Data;

@Data
public abstract class MultiAppSiteTenancyModel {

	private String appId;
	
	private String siteId;
	
	private String tenantId;
	
//	public void setAppId(String app_id);
//	
//	public String getAppId();
//	
//	public void setSiteId(String site_id);
//	
//	public String getSiteId();
//	
//	//数据所对应的租户ID	
//	public void setTenantId(String tenant_id);
//	
//	public String getTenantId();
}
