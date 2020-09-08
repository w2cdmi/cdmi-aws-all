package pw.cdmi.wishlist.model.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import pw.cdmi.wishlist.model.ProductStatus;
import pw.cdmi.wishlist.model.SaleRule;

/**
 * 愿望众筹商品表
 * @author No.11
 *
 */
@Data
@Entity
@Table(name = "wishlist_product")
public class Product {
	private String id;
	private String title;							//商品的名称或标题
	private String photoList;						//商品的展示图，JSON数组格式
	private float originalPrice;					//商品的原始价格
	private float actualPrice;						//商品的成交价格
	private int mount = 1;							//商品的数量，默认为1
	private Date salesValidity;						//众筹有效期
	private long onlookerNumber;					//围观人数
	private long participantNumber;					//参与竞标的人数
	private ProductStatus status;					//众筹状态
	private String cancelReason;					//取消原因
	private String winnerId;						//众筹获奖人的Id(会更新)
	private Date payLimitTime; 						//众筹支付限制时间
	private Date winTime; 							//众筹获奖时间（会更新）
	private long totalMoney;						//众筹总付费;
	private SaleRule salerule;								//众筹规则
}
