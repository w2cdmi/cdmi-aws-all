package pw.cdmi.open.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/****************************************************
 * 基础数据，提交手机检验信息。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2014
 ***************************************************/
@Data
@Entity
@Table(name = "p_mobile_verification")
public class MobileVerification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;						// 手机校验信息的编号

	private Long userId;				// 访问者的ID

	private String mobile; 				// 手机号码

	private String code;				// 生产的手机检验码

	private String content;				// 校验短信信息

	private Integer expireTime;				// 手机校验码的有效时间,单位小时

	private String siteId;				// 对应的应用站点

	private Date createTime;			// 检验码生产的时间
}
