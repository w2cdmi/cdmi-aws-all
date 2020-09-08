package pw.cdmi.msm.product.model;


/**
 * **********************************************************
 * <br/>
 * 枚举对象 ，表示产品信息发布状态 ，分别有产品缺货、新产品信息审核中，新产品定价审核中，新产品促销中
 * 
 * @author wuwei
 * @version cdm Service Platform, 2016年5月26日
 ***********************************************************
 */
public enum ProductPublishStatus {
	OutofStock(0,"产品缺货"),          //产品缺货
    Examine(1,"新产品审核中");         //新产品审核中

    /*value:产品状态的枚举值*/
    private int value;

    private String text;

    /**
     * 创建一个 ProductStatus 对象实例.
     *
     * @param value 产品状态枚举值
     */
    ProductPublishStatus(int value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 将传入产品状态枚举值转化为枚举对象.
     *
     * @param value 传入的产品状态枚举值
     * @return 产品状态的枚举对象
     */
    public static ProductPublishStatus fromValue(int value) {
        for (ProductPublishStatus item : ProductPublishStatus.values()) {
            if (item.value == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("0-2 is a range of parameter('value')");
    }

    public static ProductPublishStatus fromText(String text) {
        for (ProductPublishStatus item : ProductPublishStatus.values()) {
            if (item.text.equals(text)) {
                return item;
            }
        }
        throw new IllegalArgumentException(".. is a range of parameter('text')");
    }

    public static ProductPublishStatus fromEnumName(String name) {
        for (ProductPublishStatus item : ProductPublishStatus.values()) {
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
        return this.text;
    }
}
