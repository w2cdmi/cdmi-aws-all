package pw.cdmi.msm.message.model;

import org.apache.commons.lang.StringUtils;


/**
 * **********************************************************
 * <br/>
 * 消息类型枚举，包括产品、安全、服务、故障，与活动
 * 
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 ***********************************************************
 */

public enum MessageType {

	PRODUCT(0,"产品"),      		// 产品
	SECURITY(1,"安全"),      	// 安全
	SERVICE(2,"服务"),      		// 服务
	FAULT(3,"故障"),      		// 故障
	ACTIVITY(4,"活动");      	// 活动

	/* value:告警通知方式的枚举值.*/
	private int value;

	private String text;

	/**
	 *
	 * 创建一个 NotifyWay 对象实例.
	 *
	 * @param value 告警通知方式的枚举值
	 */
	MessageType(int value, String text) {
		this.value = value;
		this.text = text;
	}

	/**
	 * 将传入消息类型枚举值转化为枚举对象.
	 *
	 * @param value 传入消息类型枚举值
	 * @return 消息类型枚举对象
	 */
	public static MessageType fromValue(int value) {
		for (MessageType item : MessageType.values()) {
			if (item.value == value) {
				return item;
			}
		}
		throw new IllegalArgumentException("0-4 is a range of parameter('value')");
	}

	public static MessageType fromText(String text) {
		for (MessageType item : MessageType.values()) {
			if (item.text.equals(text)) {
				return item;
			}
		}
		throw new IllegalArgumentException("。。 is a range of parameter('value')");
	}

	public static MessageType fromEnumName(String name) {
	    if (StringUtils.isNotBlank(name)) {
            try {
                return Enum.valueOf(MessageType.class, name.trim());
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException(".. is a range of parameter('name')");
            }
        }
        return null;
	}

	public int getValue() {
		return this.value;
	}

	public String getText() {
		return text;
	}

}