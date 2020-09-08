package pw.cdmi.msm.message.model;

import lombok.Data;

@Data
public class ReferEvent {
	private EventUser user;								//事件的触发人
	private EventContent content;						//事件的内容
	private EventObject target;							//事件的目标对象
}
