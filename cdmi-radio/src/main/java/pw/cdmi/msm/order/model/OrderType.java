/**
 * 
 */
package pw.cdmi.msm.order.model;

/**
 * @author Jeffrey.Ma
 * @Date 2016年6月22日 下午2:13:54
 */
public enum OrderType {
	Create(1, "新建"), Renewal(2, "续费"), Upgrade(3, "升级");

	/* value:订单状态值. */
	private int value;
	/* value:订单状态描述. */
	private String text;

	/**
	 *
	 * 创建一个 NotifyWay 对象实例.
	 *
	 * @param value
	 *            订单类型值
	 * @param text
	 *            订单类型描述
	 */
	OrderType(int value, String text) {
		this.value = value;
		this.text = text;
	}

	/**
	 * 将传入枚举值转化为枚举对象.
	 *
	 * @param value
	 *            传入枚举值
	 * @return 枚举对象
	 */
	public static OrderType fromValue(int value) {
		for (OrderType item : OrderType.values()) {
			if (item.value == value) {
				return item;
			}
		}
		throw new IllegalArgumentException("0-1 is a range of parameter('value')");
	}

	public static OrderType fromText(String text) {
		for (OrderType item : OrderType.values()) {
			if (item.text.equals(text)) {
				return item;
			}
		}
		throw new IllegalArgumentException(".. is a range of parameter('value')");
	}

	public static OrderType fromEnumName(String name) {
		for (OrderType item : OrderType.values()) {
			if (item.toString().equals(name)) {
				return item;
			}
		}
		throw new IllegalArgumentException(".. is a range of parameter('name')");
	}

	public int getValue() {
		return this.value;
	}

	public String getText() {
		return text;
	}

}
