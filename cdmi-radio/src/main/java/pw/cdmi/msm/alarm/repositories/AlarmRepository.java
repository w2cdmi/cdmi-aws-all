package pw.cdmi.msm.alarm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.alarm.model.AlarmLevel;
import pw.cdmi.msm.alarm.model.HandleStatus;
import pw.cdmi.msm.alarm.model.entity.Alarm;

/**
 * **********************************************************
 * <br/>
 * dao接口类，提供持久化操作方法
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年6月28日
 ***********************************************************
 */
@NoRepositoryBean
public interface AlarmRepository extends PagingAndSortingRepository<Alarm, String>, QueryByExampleExecutor<Alarm> {
    
    Alarm findByAlarmCode(String alarmCode);

    Iterable<Alarm> findByObjectId(String objectId);

    Iterable<Alarm> findByContentLike(String content);

    Iterable<Alarm> findByAlarmLevel(AlarmLevel level);

    Iterable<Alarm> findByStatus(HandleStatus status);

    Iterable<Alarm> findIdsByStatus(HandleStatus status);

    Iterable<Alarm> findByAlarmLevelAndStatus(AlarmLevel level, HandleStatus status);

    Page<Alarm> findByAlarmLevel(AlarmLevel level, Pageable pageable);

    Page<Alarm> findByStatus(HandleStatus status, Pageable pageable);

    Page<Alarm> findByContentLike(String content, Pageable pageable);

    Page<Alarm> findByAlarmLevelAndStatus(AlarmLevel level, HandleStatus status, Pageable pageable);

    Page<Alarm> findByAlarmLevelAndContentLike(AlarmLevel level, String content, Pageable pageable);

    Page<Alarm> findByStatusAndContentLike(HandleStatus status, String content, Pageable pageable);

    Page<Alarm> findByAlarmLevelAndStatusAndContentLike(AlarmLevel level, HandleStatus status, String content,
        Pageable pageable);
    
    long countByAlarmLevel(AlarmLevel level);

    long countByStatus(HandleStatus status);

    long countByContentLike(String content);

    long countByAlarmLevelAndStatus(AlarmLevel level, HandleStatus status);

    long countByAlarmLevelAndContentLike(AlarmLevel level, String content);

    long countByStatusAndContentLike(HandleStatus status, String content);

    long countByAlarmLevelAndStatusAndContentLike(AlarmLevel level, HandleStatus status, String content);

}
