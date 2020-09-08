package pw.cdmi.msm.user.model.entity;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
/**
 * 账户余额
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name="p_account_blance")
public class AccountBlance {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid2")
	@Column(unique=true)
	private String siteUserId;//站点用户id
	@Column(nullable=false)
	private BigDecimal blance;//账户余额
	
	private Boolean isalarm;//是否预警
	
	private BigDecimal alarmthreshold;//预警阀值
	
	
}
