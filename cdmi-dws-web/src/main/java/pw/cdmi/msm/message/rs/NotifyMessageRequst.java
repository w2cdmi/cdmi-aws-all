package pw.cdmi.msm.message.rs;

import lombok.Data;

@Data
public class NotifyMessageRequst {
	 
	 private String type;		//消息类型
	 	
	 private String notifyId;	//推送目标
	 
	 private String content;	//json格式
	 
	 private String messageId;	//系统消息编号
	 

}
