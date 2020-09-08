package pw.cdmi.radio.user.model.entities;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.radio.model.MultiAppSiteModel;

/**
 * 关注表
 * @author No.11
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Follow  extends MultiAppSiteModel{
	private String account_id;					//关注者的ID
	private String site_account_id;				//关注者的站内账号ID
	private String target_id;					//被关注者的ID
	private String target_type;					//被关注者的类型(比如主播，房间，甚至是一篇文章)
	private Date focus_time;					//关注时间
}
