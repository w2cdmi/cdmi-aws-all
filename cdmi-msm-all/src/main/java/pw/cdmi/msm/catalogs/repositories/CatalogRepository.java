package pw.cdmi.msm.catalogs.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.catalogs.model.entities.Catalog;

@NoRepositoryBean
public interface CatalogRepository extends PagingAndSortingRepository<Catalog, String>,
QueryByExampleExecutor<Catalog> {

	Iterable<Catalog> findAllByAppId(Pageable pageable ,String appId);
}
