package pw.cdmi.msm.activity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import pw.cdmi.msm.activity.model.entities.Participate;

public interface ParticipateRepository extends PagingAndSortingRepository<Participate, String>,JpaRepository<Participate, String>{

}
