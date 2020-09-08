package pw.cdmi.msm.praise.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import pw.cdmi.core.entities.MultiApplication;
import pw.cdmi.core.entities.MultiSite;
import pw.cdmi.core.entities.MultiTenancy;

/**
 * 点赞记录表
 * @author No.11
 *
 */
@Data
@Entity
public class Praise implements MultiTenancy, MultiSite,MultiApplication{
	@Id
	private String id;
	@Column(name="app_id", nullable = false)
	private String appId;						//数据归属应用ID
	@Column(name="site_id", nullable = true)
	private String siteId;						//对应的平台内子站点Id，这个子站点可能是租户，可以是频道
	@Column(name="tenant_id", nullable = true)
	private String tenantId;					//对应的租户ID
	
	@CreatedBy
	@Column(name="account_id", nullable = false)
	private String userId;						//对应的平台账号Id
	@Column(name="account_aid", nullable = true)
	@CreatedBy
	private String userAid;						//对应的应用账号Id
	@Column(name="account_tid", nullable = true)
	@CreatedBy
	private String userTid;						//对应的租户用户账号Id
	
	
	@Column(name="target_id", nullable = false)
	private String targetId;					//点赞的对象ID
	@Column(name="target_type", nullable = false)
	private String targetType;					//点赞的对象类型，如主播的动态信息，主播本人，房间，视频, 文件
	
	@CreatedDate
	@Column(name="create_time", nullable = false)
	private Date createtime;					//该记录创建时间
	
}
