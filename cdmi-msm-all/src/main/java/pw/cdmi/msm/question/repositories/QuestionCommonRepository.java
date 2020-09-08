package pw.cdmi.msm.question.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.question.model.entities.QuestionCommon;

@NoRepositoryBean
public interface QuestionCommonRepository extends PagingAndSortingRepository<QuestionCommon, String>,QueryByExampleExecutor<QuestionCommon>{
    /**
     * 
     * 查询指定分类下的常见问题列表
     * @param classId
     * @return
     */
    public Iterable<QuestionCommon> findByClassId(String classId);
    
    /**
     * 查询添加标题的数量(判断添加标题不能重复)
     * @param title
     * @return
     */
    public long countByTitle(String title);
    
    /**
     * 
     * 通过orderNumber获取常见问题
     * @param orderNumber
     * @return
     */
    public QuestionCommon getByOrderNumber(Integer orderNumber);
    
    /**
     * 
     * 根据orderNumber查询常见问题列表并排序
     * @param orderNumber
     * @return
     */
    public Iterable<QuestionCommon> findByOrderNumber(Integer orderNumber);
    
}
