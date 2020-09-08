package pw.cdmi.radio.user.model.entities;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;

/**
 * 账号收支明细表
 * @author No.11
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Account_Finance_Detail extends MultiAppSiteModel{
	private String account_id;					//对应的账号Id
	private String site_account_id;				//对应的站点应用账号Id
	private boolean isIncome;					//收入还是支出
	private float amount;						//收入或者支出金额
	private String type;						//收入还是支出的具体类型
	private Date create_time;					//收支发生时间
	private String content;						//收支信息备注
	private String related_account_id;  		//关联的用户账号，可为空
}
