package pw.cdmi.msm.praise.repositories.jpa;

import org.springframework.data.jpa.repository.Query;

import pw.cdmi.msm.praise.repositories.PraiseRepository;

public interface JpaPraiseRepository extends PraiseRepository {
	
	@Query("from Praise where site_id= ?")
	public long countByTargetId();
}
