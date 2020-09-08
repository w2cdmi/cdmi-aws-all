package pw.cdmi.msm.alarm.repositories.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.cdmi.msm.alarm.model.HandleStatus;
import pw.cdmi.msm.alarm.repositories.AlarmRepository;

public interface JpaAlarmRepository extends AlarmRepository {

    @Query(value = "select count(o) from Alarm o where o.status = :status")
    long countByStatus(@Param(value = "status") HandleStatus status);
    
}
