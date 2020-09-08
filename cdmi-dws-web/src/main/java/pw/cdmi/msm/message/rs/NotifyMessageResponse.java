package pw.cdmi.msm.message.rs;

import java.util.Date;

import pw.cdmi.msm.message.model.EventContent;
import pw.cdmi.msm.message.model.EventObject;
import pw.cdmi.msm.message.model.EventUser;
import lombok.Data;

@Data
public class NotifyMessageResponse {
	private String id;
	private String type;
	private String title;
	private String content;
	private EventUser eventUser;
	private EventContent eventContent;
	private EventObject eventObject;
	private String createTime;
	
}
