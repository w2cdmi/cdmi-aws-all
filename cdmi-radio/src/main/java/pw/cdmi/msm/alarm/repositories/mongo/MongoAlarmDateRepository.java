package pw.cdmi.msm.alarm.repositories.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

import pw.cdmi.msm.alarm.model.entity.AlarmDate;
import pw.cdmi.msm.alarm.repositories.AlarmDateRepository;

public interface MongoAlarmDateRepository extends AlarmDateRepository {
    
    @Query(value="{'alarmId' : {'$in' : ?0}}")
    Page<AlarmDate> findByAlarmIds(Iterable<String> alarmIds, Pageable pageable);
    
}
