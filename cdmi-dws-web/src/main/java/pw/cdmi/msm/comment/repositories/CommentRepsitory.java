package pw.cdmi.msm.comment.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.comment.model.entities.Comment;

/**
 * **********************************************************
 * 接口类,提供对評論数据表的系列操作
 * @author wilson
 * @version CDMI Service Platform, 2018年1月27日
 ***********************************************************
 */
@NoRepositoryBean
public interface CommentRepsitory extends PagingAndSortingRepository<Comment, String>,QueryByExampleExecutor<Comment>{
    
	/**
	 * 根据目标id查询所有的评论
	 * @param targetId
	 * @return
	 */
	public List<Comment> findByTargetIdAndTargetType(String targetId,String targetType,Pageable pageable);
	/**
	 * 删除目标id评论
	 * @param id
	 */
	public void deleteById(String Id);
	/**
	 * 查看目标id评论
	 * @param id
	 * @return
	 */
	public Comment findById(String Id);
	/**
	 * 查看目标评论数
	 * @param targetId
	 * @return
	 */
	public long countByTargetId(String targetId);
	
	public long countByTargetIdAndTargetType(String TargetId,String TargetType);
	
	
}
