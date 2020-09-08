package pw.cdmi.open.model;

/****************************************************
 * 基础数据，枚举类型，表示应用站点的状态。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 27, 2014
 ***************************************************/
public enum SiteStatus {
	Normal,							//应用站点的状态，正常状态
	ToBeConfigured,					//应用刚部署，等待完成初始配置
	UnderMaintenance;				//应用站点处于维护状态
}
