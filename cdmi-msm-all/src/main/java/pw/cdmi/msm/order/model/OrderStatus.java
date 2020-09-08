package pw.cdmi.msm.order.model;



/**
 * **********************************************************
 * <br/>
 * 订单状态枚举
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年5月26日
 ***********************************************************
 */

public enum OrderStatus {

    Obligation(0,"待付款"),      		// 产品
    Success(1,"成功"),      			// 产品
    Close(2,"关闭");      		// 活动
	/* value:订单状态值. */
	private int value;
	/* value:订单状态描述. */
	private String text;

	/**
	 *
	 * 创建一个 NotifyWay 对象实例.
	 *
	 * @param value
	 *            订单状态值
	 * @param text
	 *            订单状描述
	 */
	OrderStatus(int value, String text) {
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
	public static OrderStatus fromValue(int value) {
		for (OrderStatus item : OrderStatus.values()) {
			if (item.value == value) {
				return item;
			}
		}
		throw new IllegalArgumentException("0-1 is a range of parameter('value')");
	}

	public static OrderStatus fromText(String text) {
		for (OrderStatus item : OrderStatus.values()) {
			if (item.text.equals(text)) {
				return item;
			}
		}
		throw new IllegalArgumentException("。。 is a range of parameter('value')");
	}

	public static OrderStatus fromEnumName(String name) {
		for (OrderStatus item : OrderStatus.values()) {
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