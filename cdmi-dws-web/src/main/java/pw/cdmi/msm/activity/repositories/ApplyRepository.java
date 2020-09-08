package pw.cdmi.msm.activity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import pw.cdmi.msm.activity.model.entities.Application;

public interface ApplyRepository extends PagingAndSortingRepository<Application, String>,JpaRepository<Application, String>{
	
}
