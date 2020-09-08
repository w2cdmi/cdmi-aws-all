package pw.cdmi.msm.alarm.model.entities;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;
import pw.cdmi.msm.alarm.model.AlarmLevel;
import pw.cdmi.msm.alarm.model.HandleStatus;

/**
 * **********************************************************
 * <br/>
 * 告警历史记录
 * 
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 ***********************************************************
 */
@Data
public class AlarmRecord {

    private String alarmId;                     	// 关联告警表中的id

    private String alarmCode;                 	 	// 告警编码

    private String content;              			// 简要描述

    @Enumerated(EnumType.STRING)
    private HandleStatus status;                    // 处理状态枚举值

    private int times;                              // 告警次数

    private String currentTime;                     // 最近告警时间

    @Enumerated(EnumType.STRING)
    private AlarmLevel level;                       // 告警级别枚举值

    private String objectName;                      // 对象名称

    private String notifyWay;                       // 通知方式名称

    public void setAlarm(Alarm alarm) {
        this.alarmCode = alarm.getAlarmCode();
        this.alarmId = alarm.getId();
        this.content = alarm.getContent();
        this.level = alarm.getAlarmLevel();
        this.status = alarm.getStatus();
        if (alarm.getNotifyWay() != null) {
            this.notifyWay = alarm.getNotifyWay().getText();
        } else {
            this.notifyWay = null;
        }
    }

}
