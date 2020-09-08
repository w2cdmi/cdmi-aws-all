package pw.cdmi.msm.complain.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import pw.cdmi.msm.complain.model.Status;
import pw.cdmi.share.model.MultiApplication;
import pw.cdmi.share.model.MultiSite;
import pw.cdmi.share.model.MultiTenancy;

@Data
@Entity(name="t_complain")
public class Complain implements MultiTenancy, MultiSite,MultiApplication{
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;
	@Column(name="target_id", nullable=false)
	private String targetId;				//投诉对象的id
	@Column(name="target_sid", nullable=true)
	private String targetSid;				//投诉对象的应用内账号id
	@Column(name="target_type", nullable=false)
	private String targetType;				//投诉对象的类型（实名审核，主播申请审核，房间申请审核，上传视频审核，动态内容审核）
	@Column(name="title", nullable=true)
	private String title;					//投诉事项标题，可为空
	@Column(name="content", nullable=false)
	private String content;					//投诉内容
	@Column(name="question_type", nullable=false)
	private String questionType;			//投诉问题的类型,政治的，色情的
	@CreatedDate
	private Date create_time;				//投诉时间
	
	private String liableman_id;			//责任人的账号Id
	private String liableman_name;			//责任人的名称
	private String receiver_Id;				//投诉接收方Id
	private String receiver_name;			//投诉接收方名称，平台运营商管理员，房间管理员
	private String receiver_type;			//投诉接收方类型，平台运营商，房间管理方
	private Status status;					//投诉事项的处理状态（核查中，确认投诉属实，已完成给出处理意见）
	private String deal_reply;				//处理回复意见
	private Date finish_time;				//投诉处理完成时间
	private String complainant_id;			//投诉人的Id
	private String complainant_sid;			//投诉人的应用内账号ID
	
	
	@Column(name="app_id", nullable = true)
	private String appId;						//数据归属应用ID
	@Column(name="site_id", nullable = true)
	private String siteId;						//对应的平台内子站点Id，这个子站点可能是租户，可以是频道
	@Column(name="tenant_id", nullable = true)
	private String tenantId;					//对应的租户ID
}