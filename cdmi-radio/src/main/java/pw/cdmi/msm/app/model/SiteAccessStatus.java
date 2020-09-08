package pw.cdmi.msm.app.model;

/**
 * **********************************************************
 * <br/>
 * 基础数据，枚举类型，表示应用的接入状态。
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年6月23日
 ***********************************************************
 */
public enum SiteAccessStatus {
    Normal,             // 正常
    NotCompleted,       // 未完成接入准备，比如应用状态为审批
    ReadyGo,            // 已准备接入，但应用尚未完成对接
    Limited,            // 有限的，或限制接入
    Trial;              // 试用阶段，一般表示不用进行计费
}
