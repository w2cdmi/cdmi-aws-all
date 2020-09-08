package pw.cdmi.msm.praise.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.praise.model.entities.Praise;
/**
 * **********************************************************
 * 接口类,提供对点赞数据表的系列操作
 * @author wilson
 * @version CDMI Service Platform, 2018年1月27日
 ***********************************************************
 */
@NoRepositoryBean
public interface PraiseRepository extends PagingAndSortingRepository<Praise, String>,QueryByExampleExecutor<Praise>{
	
	public long countByTargetId();
}
