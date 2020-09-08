package pw.cdmi.msm.message.model.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import pw.cdmi.msm.message.model.MessageStatus;

import org.hibernate.annotations.GenericGenerator;


/**
 * **********************************************************
 * <br/>
 * 通知用户信息表
 * 
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 ***********************************************************
 */
@Entity
@Table(name = "soc_notify_user_message")
@Data
public class NotifyUserMessage {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;						// 通知用户信息的ID

	private String messageId;  				// 对应消息编号

	@Column(length = 2000)
	private String userId;					// 对应用户编号

	@Enumerated(EnumType.STRING)
	private MessageStatus messageStatus;	// 消息状态枚举

	private Date notifyDate;   				// 通知时间时间
}
