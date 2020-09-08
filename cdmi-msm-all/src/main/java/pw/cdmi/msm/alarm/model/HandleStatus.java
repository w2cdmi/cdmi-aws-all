package pw.cdmi.msm.alarm.model;

import org.apache.commons.lang.StringUtils;

/**
 * **********************************************************
 * <br/>
 * 枚举对象，表示告警的处理状态，有未处理、忽略、处理中、处理完成四种状态.
 * 
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 ***********************************************************
 */
public enum HandleStatus {

    UNTREATED(0,"未处理"),   		// 未处理
    PROCESSING(1,"处理中"),      	// 处理中
    IGNORE(2,"忽略"),      		// 忽略
    FINISHED(3,"处理完成");    	// 处理完成

    /* value:告警处理状态的枚举值.*/
    private int value;

    private String text;

    /**
     * 创建一个 AlertingTreatStatus 对象实例.
     *
     * @param value 告警处理状态的枚举值
     */
    HandleStatus(int value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 将传入处理状态枚举值转化为枚举对象.
     *
     * @param value 传入告处理状态枚举值
     * @return 处理状态枚举对象
     */
    public static HandleStatus fromValue(int value) {
        for (HandleStatus item : HandleStatus.values()) {
            if (item.value == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("0-3 is a range of parameter('value')");
    }

    public static HandleStatus fromText(String text) {
        for (HandleStatus item : HandleStatus.values()) {
            if (item.text.equals(text)) {
                return item;
            }
        }
        throw new IllegalArgumentException(".. is a range of parameter('value')");
    }

    public static HandleStatus fromEnumName(String name) {
        if (StringUtils.isNotBlank(name)) {
            try {
                return Enum.valueOf(HandleStatus.class, name.trim());
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
