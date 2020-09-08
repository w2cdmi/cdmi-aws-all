package pw.cdmi.msm.message.model;

import org.apache.commons.lang.StringUtils;


/**
 * **********************************************************
 * <br/>
 * 消息状态枚举，包括未读与已读
 * 
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 ***********************************************************
 */

public enum MessageStatus {

	UNREAD(0,"未读"),      		// 未读
	READED(1,"已读");      		// 已读

	/* value:告警通知方式的枚举值.*/
	private int value;

	private String text;

	/**
	 *
	 * 创建一个 NotifyWay 对象实例.
	 *
	 * @param value 告警通知方式的枚举值
	 */
	MessageStatus(int value, String text) {
		this.value = value;
		this.text = text;
	}

	/**
	 * 将传入消息状态枚举值转化为枚举对象.
	 *
	 * @param value 传入消息状态枚举值
	 * @return 消息状态方式枚举对象
	 */
	public static MessageStatus fromValue(int value) {
		for (MessageStatus item : MessageStatus.values()) {
			if (item.value == value) {
				return item;
			}
		}
		throw new IllegalArgumentException("0-1 is a range of parameter('value')");
	}

	public static MessageStatus fromText(String text) {
		for (MessageStatus item : MessageStatus.values()) {
			if (item.text.equals(text)) {
				return item;
			}
		}
		throw new IllegalArgumentException("。。 is a range of parameter('value')");
	}

	public static MessageStatus fromEnumName(String name) {
	    if (StringUtils.isNotBlank(name)) {
            try {
                return Enum.valueOf(MessageStatus.class, name.trim());
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