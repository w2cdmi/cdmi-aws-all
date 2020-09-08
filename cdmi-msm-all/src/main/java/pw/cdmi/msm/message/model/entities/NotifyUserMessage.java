package pw.cdmi.msm.message.model.entities;


import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import pw.cdmi.msm.message.model.MessageStatus;


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
	private String id;								// 通知用户信息的ID

	@Column(length = 2000)
	private String userId;							// 对应接收信息的用户编号

	@Enumerated(EnumType.STRING)
	private MessageStatus status;					// 消息状态枚举

	private Date notifyDate;   						// 通知时间时间
	
	private Boolean isBroadcast;					// 是否是广播类消息，广播类消息会对应Message表 
	
	private String messageId;  						// 对应消息编号, 非广播内消息无
	
	private String content;					// 消息内容体，采用JSON格式进行存储，如果是广播类消息。
	
	private String refer_object_id;					// 触发该消息创建与删除时的对象的Id。
	
	private String event_object_type;								//外部自定义的一个消息关联对象类型
	private String event_object_id;									//外部自定义的消息关联对象的Id
	private String event_object_operations;			//可选，用户可以对关联对象所做的操作
	
	class MessageContent {
		private String title; 						// 消息标题，可为空
		private String content;						// 消息内容, 可为空
		private ReferEvent event;					// 消息对应的关联对象
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public ReferEvent getTarget() {
			return event;
		}
		public void setTarget(ReferEvent event) {
			this.event = event;
		}
	}
	
	//消息所关联的事件，谁什么时间对什么做了什么事情。
	class ReferEvent{
		private EventUser user;								//事件的触发人
		private EventContent content;						//事件的内容
		private EventObject target;							//事件的目标对象
		private String createtime;							//事件发送的事件
		
		public EventUser getUser() {
			return user;
		}
		public void setUser(EventUser user) {
			this.user = user;
		}
		public EventContent getContent() {
			return content;
		}
		public void setContent(EventContent content) {
			this.content = content;
		}
		public EventObject getTarget() {
			return target;
		}
		public void setTarget(EventObject target) {
			this.target = target;
		}
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}

	}
	
	//对消息关联事件的触发用户
	class EventContent{
		private String text;					//操作在页面上的显示文字
		
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}
	
	//对消息关联事件的触发用户
	class EventUser{
		private String id;					    //操作在页面上的显示文字
		private String type;					//点击操作文字所执行的事件
		private String headImageUrl;			//头像的头像
		private String name;					//用户名称
		private String degree;					//用户等级
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getHeadImageUrl() {
			return headImageUrl;
		}
		public void setHeadImageUrl(String headImageUrl) {
			this.headImageUrl = headImageUrl;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDegree() {
			return degree;
		}
		public void setDegree(String degree) {
			this.degree = degree;
		}
		
	}
	
	//对消息关联事件的事件关联对象
	class EventObject{
		private String type;								//外部自定义的一个消息关联对象类型
		private String id;									//外部自定义的消息关联对象的Id
		private Collection<Operation> Operations;			//可选，用户可以对关联对象所做的操作
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public Collection<Operation> getOperations() {
			return Operations;
		}
		public void setOperation(Collection<Operation> operations) {
			Operations = operations;
		}
	}
	
	//对消息关联对象的可采用的操作
	class Operation{
		private String text;					    //操作在页面上的显示文字
		private String serviceurl;					//点击操作文字所执行的事件
		
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getServiceurl() {
			return serviceurl;
		}
		public void setServiceurl(String serviceurl) {
			this.serviceurl = serviceurl;
		}
	}
}
