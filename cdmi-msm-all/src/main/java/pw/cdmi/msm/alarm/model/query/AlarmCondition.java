package pw.cdmi.msm.alarm.model.query;



import java.util.Date;

import lombok.Data;
import pw.cdmi.msm.alarm.model.AlarmLevel;
import pw.cdmi.msm.alarm.model.HandleStatus;

import org.apache.commons.lang.StringUtils;


/**
 * **********************************************************
 * <br/>
 * 告警信息查询条件的封装类，用于告警信息的查询
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年5月26日
 ***********************************************************
 */
@Data
public class AlarmCondition {

    private HandleStatus status;                     // 处理状态

    private String content;                          // 告警信息见姚明描述

    private AlarmLevel alarmLevel;                   // 用告警级别

    private String objectName;                       // 网元名称

    private Date startHandleDate;                    // 处理时间大于该时间

    private Date endHandleDate;                      // 处理时间小于改时间

    public String getAlarmCondition() {

        StringBuilder sb = new StringBuilder("from Alarm o, AlarmDate ad, Product p "
                + "where o.id =:ad.alarmId and r.objectId =:p.id ");
        addWhere(sb);
        sb.append(" group by o.id");
        sb.append(" order by currentTime desc");
        return sb.toString();
    }

    private StringBuilder addWhere(StringBuilder sb) {

        if (StringUtils.isNotBlank(content)) {
            sb.append(" and ").append(" o.content like '%").append(content).append("%'");
        }
        if (status != null) {
            sb.append(" and o.status =: '").append(status).append("'");
        }
        if (alarmLevel != null) {
            sb.append(" and o.alarmLevel =: '").append(alarmLevel).append("'");
        }
        if (StringUtils.isNotBlank(objectName)) {
            sb.append(" and p.name like '%").append(objectName).append("%'");
        }
        if (startHandleDate != null) {
            sb.append(" and o.handleDate >= ").append(startHandleDate);
        }
        if (endHandleDate != null) {
            sb.append(" and o.handleDate <= ").append(endHandleDate);
        }
        return sb;
    }
}
