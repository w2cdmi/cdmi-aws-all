package pw.cdmi.msm.order.model.entity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteModel;

@Data
@EqualsAndHashCode(callSuper=false)
public class InUseService extends MultiAppSiteModel{
	private String product_id;				//服务方提供的消费产品ID
	private String product_type;			//服务方提供的消费产品类型（VIP会员）
	private String consumer_id;				//对应的应用用户账号
	private String site_account_id;			//消费者的账号Id
	private Date start_time;				//服务开通时间
	private Date end_time;					//服务终止时间
	private Date create_time;				//服务创建时间
	private Date recent_update_time;		//服务信息最近更新时间
	private Date last_end_time;				//服务上次终止时间
	private String update_notes;			//服务变更信息备注（如客户缴纳了多少钱，延长服务多少天）
	private String order_id;				//对应的订单ID
	private String order_type;				//对应的订单类型(如消费记录表，商品订单表)
	private String orderSnapshot;			//订单快照
}
