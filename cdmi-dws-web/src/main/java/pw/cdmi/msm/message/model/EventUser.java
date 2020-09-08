package pw.cdmi.msm.message.model;

import lombok.Data;

@Data
public class EventUser {
	private String id;					    //操作在页面上的显示文字
	private String type;					//点击操作文字所执行的事件
	private String headImageUrl;			//头像的头像
	private String name;					//用户名称
	private String degree;					//用户等级
}
