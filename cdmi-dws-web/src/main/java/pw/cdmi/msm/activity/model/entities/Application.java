package pw.cdmi.msm.activity.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import pw.cdmi.share.model.MultiApplication;
import pw.cdmi.share.model.MultiSite;
import pw.cdmi.share.model.MultiTenancy;
import lombok.Data;

@Data
@Entity
@Table(name = "p_apply")
public class Application implements MultiTenancy, MultiSite, MultiApplication {
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "app_id", nullable = false)
	private String appId; // 数据归属应用ID

	@Column(name = "site_id", nullable = true)
	private String siteId; // 对应的平台内子站点Id，这个子站点可能是租户，可以是频道

	@Column(name = "tenant_id", nullable = true)
	private String tenantId; // 对应的租户ID

	@Column(nullable = false)
	private String activityUid; // 用户平台id

	private String activityAid; // 用户应用id

	private String activityTid; // 用户租户id

	@Column(nullable = false)
	private String activityId;

	private String userType;
	
	private String headImageUrl; // 头像的头像
	
	private String name; // 用户名称
	
	private String degree; // 用户等级

	@Column(nullable = false)
	@CreatedDate
	private Date creatTime;

	private Boolean isCost; // 是否交费

}
