package pw.cdmi.msm.complain.model.entities;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;
import pw.cdmi.msm.complain.model.Status;

@Data
@EqualsAndHashCode(callSuper=false)
public class Complain extends MultiAppSiteModel{
	private String target_id;				//投诉对象的id
	private String target_sid;				//投诉对象的应用内账号id
	private String target_type;				//投诉对象的类型（实名审核，主播申请审核，房间申请审核，上传视频审核，动态内容审核）
	private String title;					//投诉事项标题，可为空
	private String content;					//投诉内容
	private String question_type;			//投诉问题的类型,政治的，色情的
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
}