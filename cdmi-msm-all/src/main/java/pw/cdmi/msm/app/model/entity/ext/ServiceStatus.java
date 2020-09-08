package pw.cdmi.msm.app.model.entity.ext;

/**
 * **********************************************************
 * <br/>
 * 基础数据，枚举类型，表示城市服务的状态。
 * 
 * @author 
 * @version cdm Service Platform, 2017年2月15日
 ***********************************************************
 **/
public enum ServiceStatus {
	NEW,				// 新添加，尚未开通
    Normal,             // 正常，正常提供服务
    Test,               // 测试阶段，为部分人提供服务，以测试系统
    Stoped;             // 已停止服务
}
