package pw.cdmi.msm.message.model;

import lombok.Data;
@Data
public class MessageContent {
	private String title; 						// 消息标题，可为空
	private String content;						// 消息内容, 可为空
	private ReferEvent event;					// 消息对应的关联对象
}
