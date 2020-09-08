package pw.cdmi.msm.message.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import pw.cdmi.msm.message.model.MessageType;

import org.hibernate.annotations.GenericGenerator;

/**
 * ********************************************************** <br/>
 * 消息信息表
 * 
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 ***********************************************************
 */
@Entity
@Table(name = "soc_message")
@Data
public class Message {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id; 					// 消息的ID

	@Column(length = 100)
	private String title; 				// 消息标题

	@Column(length = 2000, nullable = false)
	private String content; 			// 消息内容

	private MessageType type; 	// 消息类型枚举

	private byte messaageSendState; 	// 1 表示已推送 0表示未推送
	
	private String ownerId;				// 消息的发送人，如果为系统发送的，则该字段为NULL。

	private Date createDate; 			// 创建时间
}
