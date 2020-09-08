package pw.cdmi.msm.approval.model.enties;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.radio.model.MultiAppSiteModel;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApprovalProcess extends MultiAppSiteModel{
	private String owner_id;				//审核事项表
	private String processor_id;			//当前流程处理人（角色）的Id
	private String processor_name;			//当前流程处理人名称
	private String processor_type;			//当前流程处理者的类型（如管理员，部门，机构）
	private String result;					//审批结果（核实，同意，转交他人处理，拒绝）
	private String content;					//审批意见
	private Date create_time;				//审核审批时间
}
