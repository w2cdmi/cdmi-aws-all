package pw.cdmi.msm.message.model;

import lombok.Data;

@Data
public class EventContent {
	private String id;
	private String type;
	private String text;					//操作在页面上的显示文字
}
