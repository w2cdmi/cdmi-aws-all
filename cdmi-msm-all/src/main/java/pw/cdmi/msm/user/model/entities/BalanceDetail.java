package pw.cdmi.msm.user.model.entities;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
/**
 * 收支明细
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name="p_balance_detail")
public class BalanceDetail {
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid2")
	private String serialnumbe;//流水号
	private String siteUserId;//用户id
	private Date datetime;//日期
	private String note;//备注
	private BigDecimal income;//收入
	private BigDecimal payment;//支出
	private BigDecimal amount;//余额
	private Boolean type;//交易类型
	
	
}
