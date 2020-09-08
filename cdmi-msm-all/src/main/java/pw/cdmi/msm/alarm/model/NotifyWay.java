package pw.cdmi.msm.alarm.model;

import org.apache.commons.lang.StringUtils;

/**
 * **********************************************************
 * <br/>
 * 枚举对象，表示告警通知方式，有系统、邮件两种方式
 * 
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 ***********************************************************
 */
public enum NotifyWay {

    System(0,"系统"),      	// 在程序界面上闪烁
    Mail(1,"邮件");      		// 通过邮件提醒

    /* value:告警通知方式的枚举值.*/
    private int value;

    private String text;

    /**
     *
     * 创建一个 NotifyWay 对象实例.
     *
     * @param value 告警通知方式的枚举值
     */
    NotifyWay(int value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 将传入告警通知方式枚举值转化为枚举对象.
     *
     * @param value 传入告警通知方式枚举值
     * @return 告警通知方式枚举对象
     */
    public static NotifyWay fromValue(int value) {
        for (NotifyWay item : NotifyWay.values()) {
            if (item.value == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("0-1 is a range of parameter('value')");
    }

    public static NotifyWay fromText(String text) {
        for (NotifyWay item : NotifyWay.values()) {
            if (item.text.equals(text)) {
                return item;
            }
        }
        throw new IllegalArgumentException("。。 is a range of parameter('value')");
    }

    public static NotifyWay fromEnumName(String name) {
        if (StringUtils.isNotBlank(name)) {
            try {
                return Enum.valueOf(NotifyWay.class, name.trim());
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
