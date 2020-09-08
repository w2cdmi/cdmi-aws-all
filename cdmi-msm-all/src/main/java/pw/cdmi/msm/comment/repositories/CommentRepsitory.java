package pw.cdmi.msm.comment.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.question.model.entities.QuestionClass;
/**
 * **********************************************************
 * 接口类,提供对評論数据表的系列操作
 * @author wilson
 * @version CDMI Service Platform, 2018年1月27日
 ***********************************************************
 */
@NoRepositoryBean
public interface CommentRepsitory extends PagingAndSortingRepository<QuestionClass, String>,QueryByExampleExecutor<QuestionClass>{
    /**
     * 根据名字查询问题类型存在数量
     * @param name
     * @return
     */
    public long countByName(String name);

}
