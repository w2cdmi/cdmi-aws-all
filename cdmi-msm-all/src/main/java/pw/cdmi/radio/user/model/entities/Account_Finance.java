package pw.cdmi.radio.user.model.entities;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;

/**
 * 账号财务表
 * @author No.11
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Account_Finance extends MultiAppSiteModel{
	private String account_id;					//对应的账号Id
	private String site_account_id;				//对应的站点应用账号Id
	private float total_income;					//该账号在平台上的总收入
	private float total_expenses;				//该账号在平台上的总支出
	private float total_redpacket_income;		//该账号在平台上的总的红包收入
	private float total_redpacket_expenses;		//该账号在平台上的总的红包支出
	private float total_vedio_income;			//该账号在平台上的总的视频收入
	private float total_vedio_expenses;			//该账号在平台上的总的视频支出
	private float total_act_income;				//该账号在平台上的总的直播节目收入
	private float total_act_expenses;			//该账号在平台上的总的直播节目支出
	private float total_reward_income;			//该账号在平台上获得的平台奖励收入
	private float total_game_income;			//该账号在平台上获得的平台游戏收入
	private float total_game_expenses;			//该账号在平台上获得的平台游戏支出
	private float total_share_income;			//该账号在平台上获得的主播分成收入
	private Date update_time;					//账号财务信息更新时间
}
