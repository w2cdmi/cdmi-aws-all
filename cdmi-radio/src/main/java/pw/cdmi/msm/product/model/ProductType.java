package pw.cdmi.msm.product.model;

/***
 * **********************************************************
 * <br/>
 * 枚举对象 ，表示产品类型 ，分别有弹性计算、数据库、存储、网络、云盾、监管与管理、应用、互联网中间件、域名与网站
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年6月16日
 ***********************************************************
 */
public enum ProductType {
    COMPUTER(0,"弹性计算"),
    DATABASE(1,"数据库"),
    MEMORY(2,"存储"),
    NEWWORK(3,"网络"),
    CLOUDSHIELD(4,"云盾"),
    MOITORMANAGE(5,"监控与管理"),
    APPLICATION(6,"应用"),
    INTERNETMIDDLEWARE(7,"互联网中间件"),
    DOMAINANDSITES(8,"域名与网站");

    /*value:产品类型的枚举值*/
    private int value;

    private String text;

    /**
     * 创建一个 ProductType 对象实例.
     *
     * @param value 产品类型枚举值
     */
    ProductType(int value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 将传入产品类型枚举值转化为枚举对象.
     *
     * @param value 传入的产品类型枚举值
     * @return 产品类型的枚举对象
     */
    public static ProductType fromValue(int value) {
        for (ProductType item : ProductType.values()) {
            if (item.value == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("0-2 is a range of parameter('value')");
    }

    public static ProductType fromText(String text) {
        for (ProductType item : ProductType.values()) {
            if (item.text.equals(text)) {
                return item;
            }
        }
        throw new IllegalArgumentException(".. is a range of parameter('text')");
    }

    public static ProductType fromEnumName(String name) {
        for (ProductType item : ProductType.values()) {
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
