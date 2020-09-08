package pw.cdmi.msm.product.model;


/**
 * **********************************************************
 * <br/>
 * 枚举对象 ，表示出租产品销售状态 ，分别有在售、停售
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年5月26日
 ***********************************************************
 */
public enum ProductSaleStatus {
    ONSALE(0,"在售"),            // 在售
    STOPSALE(1,"停售");         //停售

    /*value:产品状态的枚举值*/
    private int value;

    private String text;

    /**
     * 创建一个 ProductStatus 对象实例.
     *
     * @param value 产品状态枚举值
     */
    ProductSaleStatus(int value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 将传入产品状态枚举值转化为枚举对象.
     *
     * @param value 传入的产品状态枚举值
     * @return 产品状态的枚举对象
     */
    public static ProductSaleStatus fromValue(int value) {
        for (ProductSaleStatus item : ProductSaleStatus.values()) {
            if (item.value == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("0-2 is a range of parameter('value')");
    }

    public static ProductSaleStatus fromText(String text) {
        for (ProductSaleStatus item : ProductSaleStatus.values()) {
            if (item.text.equals(text)) {
                return item;
            }
        }
        throw new IllegalArgumentException(".. is a range of parameter('text')");
    }

    public static ProductSaleStatus fromEnumName(String name) {
        for (ProductSaleStatus item : ProductSaleStatus.values()) {
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
