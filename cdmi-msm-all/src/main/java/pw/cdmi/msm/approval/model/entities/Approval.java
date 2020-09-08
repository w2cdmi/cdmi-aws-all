package pw.cdmi.msm.approval.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;
import pw.cdmi.msm.approval.model.ApprovalStatus;

@Data
@EqualsAndHashCode(callSuper=false)
public class Approval extends MultiAppSiteModel{
	private String target_id;				//待审核事项对象的id
	private String target_type;				//待审核事项对象的类型（实名审核，主播申请审核，房间申请审核，上传视频审核，动态内容审核）
	private String title;					//待审核事项标题
	private String content;					//待审核事项内容
	private String catalogue_id;			//待审核事项类型Id，对应类型表
	private String submitter_id;			//申请事项提交人的账号Id
	private String submitter_name;			//申请事项提交人的账号名称或用户名称
	private ApprovalStatus status;			//待审核事项的状态（审批中，驳回待重新提交，完成）
	private String processor_id;			//当前流程处理人（角色）的Id
	private String processor_name;			//当前流程处理人名称
	private String processor_type;			//当前流程处理者的类型（如管理员，部门，机构）
	private String create_time;				//待审核事项的提交时间	
}
