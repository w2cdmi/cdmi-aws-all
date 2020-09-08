package pw.cdmi.paas.app.repositories.mongo;

import org.springframework.data.jpa.repository.Query;

import pw.cdmi.core.db.MongoRepositoryBean;
import pw.cdmi.paas.app.repositories.SiteMenuAclRepository;

@MongoRepositoryBean
public interface MongoSiteMenuAclRepository extends SiteMenuAclRepository {

	@Query("delete SiteMenuAcl where roleId= ?1")
	public int deleteByRoleId(String roleId);
}
