package pw.cdmi.core.entities;

public interface MultiSite {
	//数据所对应的site的ID，在微信第三方开放平台中，该ID对应的开发平添客户的ID，相同的数据在应用中被划分为不同的子站
	public void setSiteId(String site_id);
	
	public String getSiteId();
}
