package pw.cdmi.msm.complain.model.entities;

import java.util.Date;

import lombok.Data;

@Data
public class ComplainDealProcess {
	private String complain_id;					//对应的投诉问题编号
	private String transactor_sid;				//处理人的Id,对应site_account_id
	private String transactor_name;				//处理人名称
	private String transactor_type;				//处理人类型（运营方，直播室管理员）
	private String measures;					//采取的措施，如警告，罚款，冻结账号。
	private String content;						//采取的措施的文字描述
	private Date create_time;					//采取措施的时间
	private String liableman_sid;				//被投诉责任人,对应site_account_id
	private String liableman_name;				//责任人的名称
}