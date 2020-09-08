package pw.cdmi.msm.alarm.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pw.cdmi.msm.alarm.model.AlarmLevel;
import pw.cdmi.msm.alarm.model.HandleStatus;
import pw.cdmi.msm.alarm.model.entities.Alarm;
import pw.cdmi.msm.alarm.model.entities.AlarmDate;
import pw.cdmi.msm.alarm.model.query.AlarmCondition;

/**
 * **********************************************************
 * <br/>
 * 接口类，提供对告警信息操作的方法
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年5月26日
 ***********************************************************
 */
public interface AlarmService {

    /**
     * 
     * 向系统中添加一条告警信息
     * 
     * @param alarm 待添加的告警信息
     */
    public void createAlarm(Alarm alarm);

    /**
     * 
     * 更新告警信息.
     *
     * @param alarm 待更新的告警信息
     */
    public void updateAlarm(Alarm alarm);

    /**
     * 
     * 删除告警信息.
     *
     * @param alarmId 待删除的告警信息编号
     */
    public void deleteAlarm(String id);

    /**
     * 
     * 获得指定的告警信息.
     *
     * @param id 指定的告警信息编号
     * @return 告警信息
     */
    public Alarm getAlarm(String id);

    /**
     * 
     * 根据告警编码获得指定的告警信息.
     *
     * @param alarmCode 指定的告警编码
     * @return 告警信息
     */
    public Alarm getAlarmByAlarmCode(String alarmCode);

    /**
     * 
     * 获取所有告警信息.
     *
     * @return 告警信息列表
     */
    public Iterable<Alarm> findAllAlarm();

    /**
     * 
     * 根据处理状态查询告警条数.
     *
     * @param status 处理状态
     * @return 告警条数
     */
    public Long getCountAlarmByStatus(HandleStatus status);

    /**
     * 
     * 根据告警Id获取改告警时间的列表.
     *
     * @param alarmId 告警信息编号
     * @return 告警时间信息列表
     */
    public List<AlarmDate> findAlarmDateByAlarmId(String alarmId);

    /**
     * 
     * 根据处理状态查询告警时间信息.
     *
     * @param status 处理状态
     * @param pageNo 当前页数
     * @param pageSize 每页大小
     * @return 告警时间信息列表
     */
    public Page<AlarmDate> findAlarmDatePageByStatus(HandleStatus status, int pageNo, int pageSize);

    /**
     * 
     * 以分页的方式获取告警历史记录信息列表.
     *
     * @param content 告警简要描述
     * @param status 处理状态
     * @param level 告警级别
     * @param objectName 对象的名称
     * @param pageNo 当前页数
     * @param pageSize 每页大小
     * @return 返回告警历史记录信息列表
     */
    public Page<Alarm> findAlarmByCondition(String content, HandleStatus status, AlarmLevel level,
        String objectName, int pageNo, int pageSize);

    /**
     * 
     * 根据条件获取告警历史记录的条数.
     *
     * @param content 告警简要描述
     * @param status 处理状态
     * @param level 告警级别
     * @param objectName 对象的名称
     * @return 返回告警历史记录条数
     */
    public Long getCountAlarmByCondition(String content, HandleStatus status, AlarmLevel level, String objectName);

    /**
     * 
     * 以分页的方式获取告警信息列表.
     *
     * @param condition 查询条件对象
     * @param pageNo 当前页数
     * @param pageSize 每页大小
     * @return 返回告警信息列表
     */
    public Page<Alarm> findAlarm(AlarmCondition condition, int pageNo, int pageSize);

    /**
     * 
     * 根据告警级别与处理状态分页查询
     * 
     * @param level 告警级别
     * @param status 处理状态
     * @param pageNo 当前页数
     * @param pageSize 每页大小
     * @return 返回告警信息列表
     */
    public Page<Alarm> findAlarmPageByLevelAndStatus(AlarmLevel level, HandleStatus status, int pageNo, int pageSize);

    // 告警处理
    /**
     * 
     * 处理告警信息
     *
     * @param id 告警信息Id
     * @param handleUserId 处理用户Id
     * @param status 需要更新的状态
     * @param handleWay 处理方式
     */
    public void resolveCurrentAlarm(String id, String handleUserId, HandleStatus status, String handleWay);

}
