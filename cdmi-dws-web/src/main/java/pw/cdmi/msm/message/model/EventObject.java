package pw.cdmi.msm.message.model;

import lombok.Data;

@Data
public class EventObject {
	private String type;								//外部自定义的一个消息关联对象类型
	private String id;									//外部自定义的消息关联对象的Id
//	private Collection<Operation> Operations;			//可选，用户可以对关联对象所做的操作
}
