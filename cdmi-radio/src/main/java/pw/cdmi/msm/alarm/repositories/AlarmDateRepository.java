package pw.cdmi.msm.alarm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.alarm.model.entity.AlarmDate;

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
public interface AlarmDateRepository extends PagingAndSortingRepository<AlarmDate, String>,
    QueryByExampleExecutor<AlarmDate> {

    Iterable<AlarmDate> findByAlarmId(String alarmId);
    
    Page<AlarmDate> findByAlarmIds(Iterable<String> alarmIds, Pageable pageable);
    
    long countByAlarmId(String alarmId);

}
