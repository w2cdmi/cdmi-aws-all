package pw.cdmi.msm.order.model.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import pw.cdmi.msm.order.model.OrderStatus;
import pw.cdmi.msm.order.model.OrderType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * **********************************************************
 * <br/>
 * 订单信息表
 * 
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 ***********************************************************
 */

@Data
@Entity
@Table(name = "p_order")
@Document(collection="p_order")
public class Order {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;                  // 订单的ID

    @Column(length = 100)
    private String orderId;        		// 订单号。根据一定规则自动生成

    @Column(length = 2000)
    private String objectId;            // 对象的id
    @Column
    private String userId;           	// 用户的id
    @Column
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;         // 消息类型枚举
    @Column
    private Date submitDate;            // 订单提交时间
    @Column
    private Date payDate;               // 支付时间
    @Column
    private String description;         // 订单描述
    
    @Enumerated(EnumType.STRING)
    private  OrderType type;			//订单类型
    
}
