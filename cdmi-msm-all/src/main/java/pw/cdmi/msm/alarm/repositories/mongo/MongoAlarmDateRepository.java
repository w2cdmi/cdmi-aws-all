package pw.cdmi.msm.alarm.repositories.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

import pw.cdmi.core.db.MongoRepositoryBean;
import pw.cdmi.msm.alarm.model.entities.AlarmDate;
import pw.cdmi.msm.alarm.repositories.AlarmDateRepository;

@MongoRepositoryBean
public interface MongoAlarmDateRepository extends AlarmDateRepository {
    
    @Query(value="{'alarmId' : {'$in' : ?0}}")
    Page<AlarmDate> findByAlarmIds(Iterable<String> alarmIds, Pageable pageable);
    
}
