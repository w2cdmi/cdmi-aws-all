package pw.cdmi.msm.alarm.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import pw.cdmi.msm.alarm.model.AlarmLevel;
import pw.cdmi.msm.alarm.model.HandleStatus;
import pw.cdmi.msm.alarm.model.NotifyWay;

/**
 * ********************************************************** <br/>
 * 业务告警信息表
 * 
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 ***********************************************************
 */

@Data
@Entity
@Table(name = "soc_alarm")
@Document(collection = "soc_alarm")
public class Alarm {
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
    private String id; 				// 告警的ID

    private String objectId; 		// 对应业务对象编号

    @Column(unique = true)
    private String alarmCode;		// 告警编码 ，根据一定规则生成作为唯一标示

    private String content; 		// m告警信息简要描述

    @Enumerated(EnumType.STRING)
    private AlarmLevel alarmLevel; 	// 告警级别,对应告警级别枚举

    @Enumerated(EnumType.STRING)
    private NotifyWay notifyWay; 	// 通知方式 String 对应通知方式枚举

    @Enumerated(EnumType.STRING)
    private HandleStatus status; 	// 处理状态 ,对应处理状态枚举

    private String handleUserId; 	// 处理用户id

    @Column(length = 2000)
    private String handleWay; 		// 处理方法 ,方法收集以便下次遇到类似告警能快速处理

    private Date handleDate; 		// 处理时间

    @Column(length = 2000)
    private String description; 	// 详细描述

    /**************************** 冗余字段 ***************************/
    @Transient
    private int times; 				// 告警次数

    @Transient
    private String currentTime; 	// 最近告警时间

    @Transient
    private String objectName; 		// 对象名称
    

}