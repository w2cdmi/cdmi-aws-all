package pw.cdmi.msm.alarm.model;

import org.apache.commons.lang.StringUtils;


/**
 * **********************************************************
 * <br/>
 * 枚举对象 ，表示告警级别的类型 ，分别有通知、待定告警、警告告警、次要告警、重大告警、严重告警
 * 
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 ***********************************************************
 */
public enum AlarmLevel {

	GENERAL(0,"一般"),            // 一般警告
	SERIOUS(1,"严重"),            // 严重告警
	CRITICAL(2,"致命");          	// 致命

	/*value:告警级别的枚举值*/
	private int value;

	private String text;

	/**
	 * 创建一个 AlarmLevel 对象实例.
	 *
	 * @param value 告警级别枚举值
	 */
	AlarmLevel(int value, String text) {
		this.value = value;
		this.text = text;
	}

	/**
	 * 将传入告警级别枚举值转化为枚举对象.
	 *
	 * @param value 传入的告警级别枚举值
	 * @return 告警级别的枚举对象
	 */
	public static AlarmLevel fromValue(int value) {
		for (AlarmLevel item : AlarmLevel.values()) {
			if (item.value == value) {
				return item;
			}
		}
		throw new IllegalArgumentException("0-2 is a range of parameter('value')");
	}

	public static AlarmLevel fromText(String text) {
		for (AlarmLevel item : AlarmLevel.values()) {
			if (item.text.equals(text)) {
				return item;
			}
		}
		throw new IllegalArgumentException(".. is a range of parameter('value')");
	}

	public static AlarmLevel fromEnumName(String name) {
        if (StringUtils.isNotBlank(name)) {
            try {
                return Enum.valueOf(AlarmLevel.class, name.trim());
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
		return this.text;
	}

}
