package pw.cdmi.msm.praise.repositories;

import java.util.List;



import org.springframework.data.domain.Pageable;
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

 public interface PraiseRepsitory extends PagingAndSortingRepository<Praise, String>,QueryByExampleExecutor<Praise>{
	
	 public long countByTargetIdAndTargetType(String targetId,String TargetType);
	 
	 public List<Praise> findByTargetIdAndTargetType(String targetId,String TargetType,Pageable pageable);
	 
	//检查是否已经点赞
	 public Praise findByTargetIdAndUserAidAndTargetType(String targetId,String userAid,String targetType);
}
