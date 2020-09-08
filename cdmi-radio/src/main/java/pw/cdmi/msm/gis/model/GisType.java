package pw.cdmi.msm.gis.model;

/**
 * **********************************************************
 * <br/>
 * 当前国内常用的地图坐标系.<br/>
 * 
 * @author 伍伟
 * @version cdm Service Platform, 2016年5月31日
 ***********************************************************
 */
public enum GisType {
    Mars(1),        //中国测绘局搞的火星坐标系
    Google(2),      //Google地图坐标系,sogou经纬度
    Baidu(3),       //百度地图坐标系
    MapBar(4),      //MapBar地图坐标系
    GPS(5),         //GPS坐标系
    Gogou(6);       //搜狗地图坐标系,墨卡托坐标
    
    private int value;

    private GisType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
}
