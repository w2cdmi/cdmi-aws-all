package pw.cdmi.msm.user.model.entities;


import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
/**
 * **********************************************************
 * <br/>
 * TODO消费明细.<br/>
 * 
 * @author Administrator
 * @version cdm Service Platform, 2016年5月27日
 ***********************************************************
 */
@Data
@Entity
@Table(name="p_consumer_detail")
public class ConsumerDetail {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid" ,strategy="uuid2")
	private String id;
	private String productId;//产品名称
	private Date payTime;//消费时间
	private BigDecimal price;//消费金额
	private String ordernumber;//账单号
	private String siteUserId;//用户id
}
