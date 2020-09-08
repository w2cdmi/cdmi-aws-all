package pw.cdmi.msm.order.model.entity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;

/**
 * 消费记录表
 * @author No.11
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Consumed extends MultiAppSiteModel{
	private String consumer_id;					//消费者的账号Id
	private String site_account_id;				//消费者的站内账号ID
	private double amount;						//消费金额
	private String currency_type;				//消费的货币金额（如钻石）
	private Date create_time;					//消费时间
	private String provider_id;					//提供服务方的Id
	private String provider_type;				//提供服务方的类型（房间，主播）
	private String product_id;					//服务方提供的消费产品ID
	private String product_type;				//服务方提供的消费产品类型（动态，直播）
	private String spending_unit;				//消费单位（如分钟，个）
	private String spending_amount;				//消费的数量
}
