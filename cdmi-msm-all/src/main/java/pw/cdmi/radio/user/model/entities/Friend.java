package pw.cdmi.radio.user.model.entities;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;

/**
 * 好友表
 * @author No.11
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Friend extends MultiAppSiteModel{
	private String account_id;					//好友A的账号ID（一个独立的索引）（谁主动加就谁就是A）
	private String account_sid;					//好友的站内账号ID
	private String friend_id;					//好友B的账号ID（一个独立的索引）
	private String friend_sid;					//好友的站内账号ID
	private String friend_name_note;			//好友备注
	private Date create_time;					//好友时间，需要相互添加后才能成为好友
	private boolean synchronizedCreate; 		//是否同步添加，如果是同步的话，如果A添加B后，B也会添加A
	private boolean synchronizedDelete; 		//是否同步删除，如果是同步的话，如果A删除B后，B也会删除A
}
