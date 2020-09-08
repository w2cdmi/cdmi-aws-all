package pw.cdmi.msm.message.rs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;
import pw.cdmi.msm.message.model.MessageType;
@Data
public class MessageRequest {
	private String ownerId;
	private String title; 				// 消息标题
	private String content; 			// 消息内容
	private MessageType messageType; 	// 消息类型枚举
}
