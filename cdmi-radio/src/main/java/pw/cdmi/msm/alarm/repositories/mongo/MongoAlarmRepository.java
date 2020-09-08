package pw.cdmi.msm.alarm.repositories.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

import pw.cdmi.msm.alarm.model.AlarmLevel;
import pw.cdmi.msm.alarm.model.HandleStatus;
import pw.cdmi.msm.alarm.model.entity.Alarm;
import pw.cdmi.msm.alarm.repositories.AlarmRepository;

public interface MongoAlarmRepository extends AlarmRepository {

    @Query(count = true, value = "{'status' : ?0 }")
    long countByStatus(HandleStatus status);
    
    @Query(value = "{'alarmLevel' : ?0, 'status' : ?1}")
    Page<Alarm> findByAlarmLevelAndStatus(AlarmLevel level, HandleStatus status, Pageable pageable);

    @Query(value = "{'status' : ?0 }", fields = "{id:1}")
    Iterable<Alarm> findIdsByStatus(HandleStatus status);

}
