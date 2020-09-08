package pw.cdmi.msm.order.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.msm.order.model.PayChannel;
import pw.cdmi.msm.order.model.PaymentStatus;
import pw.cdmi.radio.model.MultiAppSiteModel;

/**
 * 充值流水记录表
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="p_rechange_water")
public class RechangeWater extends MultiAppSiteModel{
    
	private String account_id;
	
    @Column(nullable=false)
    private String site_account_id;
    
    @Column(nullable=false)
    private BigDecimal amount;						//充值金额
    
    private PayChannel payChannel;					//支付渠道（微信支付，支付宝）
    
    private Date create_time;						//交易时间
    
    private PaymentStatus status;				    //充值状态
    
    private String order_iid;						//对应的订单编号（方便与第三方支付渠道进行对账)

    @Column(nullable=false)
    private String order_id;						//对应的订单编号（方便与第三方支付渠道进行对账)
    
    private String product_id;						//对应的产品编号（方便与第三方支付渠道进行对账)
    
    private String product_title;					//对应的产品名称（方便与第三方支付渠道进行对账)
    
    private String payInfo;							//支付描述信息，谁使用了那个方式充值了多少钱
}
