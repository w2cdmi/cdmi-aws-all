package pw.cdmi.msm.alarm.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * **********************************************************
 * <br/>
 * 告警时间表，用于显示同一类告警信息出现的时间详情
 * 
 * @author Liujh
 * @version cdmi Service Platform, 2016年5月26日
 ***********************************************************
 */
@Data
@Entity
@Table(name = "soc_alarm_date")
@Document(collection = "soc_alarm_date")
public class AlarmDate {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;				// 国家的ID

    private String alarmId;			// 对应告警编号

    private Date alarmDate;			// 出现告警的具体时间

}
