package pw.cdmi.msm.alarm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.alarm.model.AlarmLevel;
import pw.cdmi.msm.alarm.model.HandleStatus;
import pw.cdmi.msm.alarm.model.NotifyWay;
import pw.cdmi.msm.alarm.model.entities.Alarm;
import pw.cdmi.msm.alarm.model.entities.AlarmDate;
import pw.cdmi.msm.alarm.model.query.AlarmCondition;
import pw.cdmi.msm.alarm.repositories.AlarmDateRepository;
import pw.cdmi.msm.alarm.repositories.AlarmRepository;
import pw.cdmi.msm.alarm.service.AlarmService;

/**
 * **********************************************************
 * <br/>
 * 实现类，提供对告警信息的实现方法
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年5月26日
 ***********************************************************
 */
@Service
public class AlarmServiceImpl implements AlarmService {

    private static Logger log = LoggerFactory.getLogger(AlarmServiceImpl.class);

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // @Autowired
    // private GenericJPAHibernateImpl jpaImpl;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private AlarmDateRepository alarmDateRepository;

    // @Autowired
    // private ProductService productService;

    @Transactional
    @Override
    public void createAlarm(Alarm alarm) {
        try {
            Alarm alarming = getAlarmByAlarmCode(alarm.getAlarmCode());
            AlarmDate alarmDate = new AlarmDate();
            // 判断数据库中是否有相同告警信息
            if (alarming != null) {
                // 创建关联，将告警id关联到告警时间表中
                alarmDate.setAlarmId(alarming.getId());
                alarmDate.setAlarmDate(new Date());
                // jpaImpl.save(alarmDate);
                alarmDateRepository.save(alarmDate);
            } else {
                // 创建告警信息时，状态为为处理
                alarm.setStatus(HandleStatus.UNTREATED);
                // 默认通知方式为系统通知
                alarm.setNotifyWay(NotifyWay.System);
                // jpaImpl.save(alarm);
                alarmRepository.save(alarm);
                if (alarm.getId() != null) {
                    alarmDate.setAlarmId(alarm.getId());
                    alarmDate.setAlarmDate(new Date());
                    // jpaImpl.save(alarmDate);
                    alarmDateRepository.save(alarmDate);
                }
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void updateAlarm(Alarm alarm) {
        try {
            // jpaImpl.update(alarm);
            alarmRepository.save(alarm);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void deleteAlarm(String id) {
        try {
            // Alarm alarm = getAlarm(id);
            // if (alarm != null) {
            // //jpaImpl.delete(alarm);
            // }
            alarmRepository.delete(id);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Alarm getAlarm(String id) {
        try {
            // return jpaImpl.get(id, Alarm.class);
            return alarmRepository.findOne(id);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Alarm getAlarmByAlarmCode(String alarmCode) {
        // String jpql = "from Alarm where alarmCode =:alarmCode";
        try {
            // JPQuery query = JPQuery.createQuery(jpql);
            // query.setParamater("alarmCode", alarmCode);
            // return jpaImpl.findOne(query);
            return alarmRepository.findByAlarmCode(alarmCode);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<Alarm> findAllAlarm() {
        try {
            // return jpaImpl.findAll(Alarm.class);
            return alarmRepository.findAll();
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Long getCountAlarmByStatus(HandleStatus status) {
        try {
            return alarmRepository.countByStatus(status);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public List<AlarmDate> findAlarmDateByAlarmId(String alarmId) {
        List<AlarmDate> list = new ArrayList<AlarmDate>();
        // String jpql = "from AlarmDate o where o.alarmId =:alarmId order by o.alarmDate desc";
        try {
            // JPQuery query = JPQuery.createQuery(jpql);
            // query.setParamater("alarmId", alarmId);
            // return jpaImpl.find(query);
            Iterable<AlarmDate> alarmDates = alarmDateRepository.findByAlarmId(alarmId);
            Iterator<AlarmDate> iterList = alarmDates.iterator();
            while (iterList.hasNext()) {
                list.add(iterList.next());
            }
            return list;
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Page<AlarmDate> findAlarmDatePageByStatus(HandleStatus status, int pageNo, int pageSize) {
        List<String> list = new ArrayList<String>();
        // String jpql1 = "select o.id from Alarm o where o.status =:status";
        try {
            // JPQuery query = JPQuery.createQuery(jpql1);
            // query.setParamater("status", status);
            // List<String> alarmIdlist = jpaImpl.find(query);
            // String alarmIds = list2String(alarmIdlist);
            // String jpql = "from AlarmDate o where o.alarmId IN " + alarmIds + " order by o.alarmDate desc";
            // JPQuery jpquery = JPQuery.createQuery(jpql);
            // return jpaImpl.find(jpquery, pageNo, pageSize);
            Iterable<Alarm> alarms = alarmRepository.findIdsByStatus(status);
            Iterator<Alarm> iterList = alarms.iterator();
            while (iterList.hasNext()) {
                list.add(iterList.next().getId());
            }
            Pageable pageable = new PageRequest(pageNo, pageSize);
            return alarmDateRepository.findByAlarmIdIn(list, pageable);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Page<Alarm> findAlarm(AlarmCondition condition, int pageNo, int pageSize) {
        try {
            // String jpql = condition.getAlarmCondition();
            // return jpaImpl.find(jpql, pageNo, pageSize);
            Pageable pageable = new PageRequest(pageNo, pageSize);
            return alarmRepository.findAll(pageable);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Page<Alarm> findAlarmByCondition(String content, HandleStatus status, AlarmLevel level, String objectName,
        int pageNo, int pageSize) {
        Page<Alarm> page = null;
        // StringBuilder sb = new StringBuilder("select o from Alarm o where o.id IS NOT EMPTY");
        try {
            // if (StringUtils.isNotBlank(content)) {
            // sb.append(" and o.content like '%" + content + "%'");
            // }
            // if (status != null) {
            // sb.append(" and o.status = :status");
            // }
            // if (level != null) {
            // sb.append(" and o.alarmLevel = :alarmLevel");
            // }
            // if (StringUtils.isNotBlank(objectName)) {
            // String s = "select p.id from Product p where p.name like '%" + objectName + "%'";
            // JPQuery query = JPQuery.createQuery(s);
            // List<String> pIdlist = jpaImpl.find(query);
            // sb.append(" and o.objectId IN (select new Product(p.id) from Product p where p.name like '%")
            // .append(objectName).append("%')");
            // String productIds = list2String(pIdlist);
            // sb.append(" and o.objectId IN " + productIds);
            // }
            // String jpql = sb.toString();
            // JPQuery jpquery = JPQuery.createQuery(jpql);
            // jpquery.setParamater("status", status);
            // jpquery.setParamater("alarmLevel", level);
            //
            // return jpaImpl.find(jpquery, pageNo, pageSize);
            Pageable pageable = new PageRequest(pageNo, pageSize);
            if (StringUtils.isNotBlank(content)) {
                if(status != null){
                    if(level != null){
                        page = alarmRepository.findByAlarmLevelAndStatusAndContentLike(level, status, content, pageable);
                    }else{
                        page = alarmRepository.findByStatusAndContentLike(status, content, pageable);
                    }
                }else{
                    if(level != null){
                        page = alarmRepository.findByAlarmLevelAndContentLike(level, content, pageable);
                    }else{
                        page = alarmRepository.findByContentLike(content, pageable);
                    }
                }
            }else if(status != null){
                if(level != null){
                    page = alarmRepository.findByAlarmLevelAndStatus(level, status, pageable);
                }else{
                    page = alarmRepository.findByStatus(status, pageable);
                }
            }else{
                page = alarmRepository.findAll(pageable);
            }
            return page;
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Long getCountAlarmByCondition(String content, HandleStatus status, AlarmLevel level, String objectName) {
        long count = 0l;
        // StringBuilder sb = new StringBuilder("select o.id from Alarm o where o.id IS NOT EMPTY");
        try {
            // if (StringUtils.isNotBlank(content)) {
            // sb.append(" and o.content like '%" + content + "%'");
            // }
            // if (status != null) {
            // sb.append(" and o.status = :status");
            // }
            // if (level != null) {
            // sb.append(" and o.alarmLevel = :alarmLevel");
            //
            // }
            // if (StringUtils.isNotBlank(objectName)) {
            // String s = "select p.id from Product p where p.name like '%" + objectName + "%'";
            // JPQuery query = JPQuery.createQuery(s);
            // List<String> pIdlist = jpaImpl.find(query);
            // String productIds = list2String(pIdlist);
            // sb.append(" and o.objectId IN " + productIds);
            // }
            // String jpql = sb.toString();
            // JPQuery jpquery = JPQuery.createQuery(jpql);
            // jpquery.setParamater("alarmLevel", level);
            // jpquery.setParamater("status", status);
            //
            // List<String> list = jpaImpl.find(jpquery);
            // return (long) list.size();
            if (StringUtils.isNotBlank(content)) {
                if(status != null){
                    if(level != null){
                        count = alarmRepository.countByAlarmLevelAndStatusAndContentLike(level, status, content);
                    }else{
                        count = alarmRepository.countByStatusAndContentLike(status, content);
                    }
                }else{
                    if(level != null){
                        count = alarmRepository.countByAlarmLevelAndContentLike(level, content);
                    }else{
                        count = alarmRepository.countByContentLike(content);
                    }
                }
            }else if(status != null){
                if(level != null){
                    count = alarmRepository.countByAlarmLevelAndStatus(level, status);
                }else{
                    count = alarmRepository.countByStatus(status);
                }
            }else{
                count = alarmRepository.count();
            }
            return count;
            //return alarmRepository.count();
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Transactional
    @Override
    public void resolveCurrentAlarm(String id, String handleUserId, HandleStatus status, String handleWay) {
        try {
            Alarm alarm = getAlarm(id);
            if (alarm != null) {
                alarm.setAlarmCode(handleUserId);
                alarm.setStatus(status);
                alarm.setHandleWay(handleWay);
                alarm.setHandleDate(new Date());
                updateAlarm(alarm);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    /*@SuppressWarnings("rawtypes")
    public String list2String(List list) {
        return String.valueOf(list).replace("[", "('").replace("]", "')").replaceAll(", ", "', '");
    }*/

    @Override
    public Page<Alarm> findAlarmPageByLevelAndStatus(AlarmLevel level, HandleStatus status, int pageNo, int pageSize) {
        try {
            Pageable pageable = new PageRequest(pageNo, pageSize);
            return alarmRepository.findByAlarmLevelAndStatus(level, status, pageable);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

}
