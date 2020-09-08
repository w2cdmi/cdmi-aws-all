package pw.cdmi.core.entities;

public interface MultiTenancy{
	//数据所对应的租户ID	
	public void setTenantId(String tenant_id);
	
	public String getTenantId();
}
