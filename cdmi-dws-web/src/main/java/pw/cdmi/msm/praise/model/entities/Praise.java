package pw.cdmi.msm.praise.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import pw.cdmi.share.model.MultiApplication;
import pw.cdmi.share.model.MultiSite;
import pw.cdmi.share.model.MultiTenancy;

/**
 * 点赞记录表
 * @author No.11
 *
 */
@Data
@Entity
@Table(name="t_praise",indexes = {
		@Index(name  = "praise_1",columnList="target_id")
})
public class Praise implements MultiTenancy, MultiSite,MultiApplication{
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;
	
	@Column(name="app_id", nullable = false)
	private String appId;						//数据归属应用ID
		
	@Column(name="tenant_id", nullable = true)
	private String tenantId;					//对应的租户ID
	
	@Column(name="site_id", nullable = true)
	private String siteId;						//对应的平台内子站点Id，这个子站点可能是租户，可以是频道
	
	@CreatedBy
	@Column(name="praiser_id", nullable = true)
	
	private String userUid;						//对应的平台账号Id
	
	@Column(name="praiser_aid", nullable = false)	
	@CreatedBy
	private String userAid;						//对应的应用账号Id
	
	@Column(name="praiser_tid", nullable = true)
	@CreatedBy
	private String userTid;						//对应的租户账号Id
	
	@Column(name="praiser_name", nullable = true)
	private String userName;
	@Column(name="praiser_type", nullable = true)
	private String userType;

	private String ownerId;
	@Column(name="head_Image", nullable = true)
	private String headImage;					//点赞的对象的头像地址
	
	@Column(name="target_id", nullable = false)
	private String targetId;					//点赞的对象ID
	
	@Column(name="target_type", nullable = false)
	private String targetType;					//点赞的对象类型，如主播的动态信息，主播本人，房间，视频, 文件
	
	@CreatedDate
	@Column(name="create_time", nullable = false)
	private Date createTime;					//该记录创建时间
	
	 public Praise(){  
		  
	 }
	
}
