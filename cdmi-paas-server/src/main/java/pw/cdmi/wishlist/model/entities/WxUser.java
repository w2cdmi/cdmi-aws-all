package pw.cdmi.wishlist.model.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * 微信用户信息表
 * @author No.11
 *
 */
@Data
@Entity
@Table(name = "p_wxuser")
public class WxUser {
	private String id;
	private String accountId;					//对应UserAccount表
	private String wxOpenId;
	private String wxUnionId;
	private String nick;
	private String headImageUrl;
	private String phoneNumber;
	private String address;
	private String sponsorId;					//邀请人，推荐人
	private String sponsortype;					//邀请人类型，这里对应的WXUser
	private long inviteCount;					//邀请人员的数量
}
