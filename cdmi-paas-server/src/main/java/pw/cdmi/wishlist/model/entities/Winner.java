package pw.cdmi.wishlist.model.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * 愿望众筹商品获奖表
 * @author No.11
 *
 */
@Data
@Entity
@Table(name = "wishlist_winner")
public class Winner {
	private String id;
	private String productId;
	private String wxuserId;						//中奖人员的微信用户Id
	private boolean isPayed;						//是否完成支付
	private Date payedTime;							//支付完成时间
	private boolean isGiveUp = false;				//是否放弃领奖
	private Date giveUpTime;						//放弃领奖时间
	private Date creatime;							//宣布中奖的时间
}
