package pw.cdmi.msm.activity.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

import pw.cdmi.msm.activity.model.ActivityStateSupper;
import pw.cdmi.msm.activity.model.ActivityTypeSupper;
import pw.cdmi.share.model.MultiApplication;
import pw.cdmi.share.model.MultiSite;
import pw.cdmi.share.model.MultiTenancy;
@Data
@Entity
@Table(name = "p_activity")
public class Activity  implements MultiTenancy, MultiSite, MultiApplication {
	//------------ id
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
	private String activityUid;		//用户平台id
	
	private String activityAid;		//用户应用id
	
	private	String activityTid;		//用户租户id
	
	
	//活动相关设置
	@Column(nullable = false)
	private String activityName;   		//活动的名称
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ActivityTypeSupper activityType;		//活动类型
	
	private String activityDescribe;	//活动简介
	
	private String activityArranges;	//活动安排json数组
	
	private String historySilhouettes;  //历史剪影
	
	@Enumerated(EnumType.STRING)
	private ActivityStateSupper activityState;		//活动的状态，枚举
		
	private Boolean isLimitNumber;		//是否开启人数限制
	
	private Integer limitNumber;		//限制人数
	
	private Boolean isActivityCost;		//活动是否收费
	
	private Integer activityCost;		//活动费用
		
	private Boolean	isApply;			//是否开启报名
	
	private Integer numberOfApplicants;	//报名人数	

	private Boolean	isApplyCost;		//是否有报名费用
	
	private Integer applyCost;			//报名费用
	
	private Integer numberOfFollowers;	//关注人数
	
	private Integer participation;		//参与人数，(不含组织方于嘉宾);
	
	private String listNews;			//相关新闻json
	
	private String sponsorId;			//
	
	private String sponsorNames;			//主办方名字
	
	private String guests;			//嘉宾名字列表

			//活动时间 时间设置
	@Column(nullable = false)
	private Date createTime;			//创建时间
	
	private Date updateTime;			//更新时间
	
	private Date startTime;				//开始时间
	
	private Date endTime;				//结束时间

	
}
