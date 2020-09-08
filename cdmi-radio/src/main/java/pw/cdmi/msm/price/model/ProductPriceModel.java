package pw.cdmi.msm.price.model;

/**
 * **********************************************************
 * <br/>
 * 枚举对象 ，表示产品定价模式 ，分别有年/月、流量计算
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年6月16日
 ***********************************************************
 */
public enum ProductPriceModel {
    YEARORMONTH(0,"年/月"),            
    FLOW(1,"流量");

    /*value:产品类型的枚举值*/
    private int value;

    private String text;

    /**
     * 创建一个 ProductPriceModel 对象实例.
     *
     * @param value 定价模式枚举值
     */
    ProductPriceModel(int value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 将传入定价模式枚举值转化为枚举对象.
     *
     * @param value 传入的定价模式枚举值
     * @return 定价模式的枚举对象
     */
    public static ProductPriceModel fromValue(int value) {
        for (ProductPriceModel item : ProductPriceModel.values()) {
            if (item.value == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("0-2 is a range of parameter('value')");
    }

    public static ProductPriceModel fromText(String text) {
        for (ProductPriceModel item : ProductPriceModel.values()) {
            if (item.text.equals(text)) {
                return item;
            }
        }
        throw new IllegalArgumentException(".. is a range of parameter('text')");
    }

    public static ProductPriceModel fromEnumName(String name) {
        for (ProductPriceModel item : ProductPriceModel.values()) {
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
