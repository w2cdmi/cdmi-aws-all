package pw.cdmi.msm.question.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.question.model.QuestionStatus;
import pw.cdmi.msm.question.model.entities.Question;

@NoRepositoryBean
public interface QuestionRepository extends PagingAndSortingRepository<Question, String>,QueryByExampleExecutor<Question>{
    /**
     * 查询用户的提问列表
     * @param id
     * @return
     */
    public Iterable<Question> findQuestionsBySiteUserId(String id);
    
    /**
     * 根据问题状态查询用户的问题列表
     * @param status
     * @return
     */
    public Iterable<Question> findQuestionBySiteUserIdAndStatus(String siteUserId,QuestionStatus status);
    
    /**
     * 
     * 
     * @param classId
     * @return
     */
    public Iterable<Question> findByClassId(String classId);
    
    /**
     * 
     * 根据问题状态获取指定问题类型下的问题列表
     * @param classId
     * @param status
     * @return
     */
    public Iterable<Question> findByClassIdAndStatus(String classId,QuestionStatus status);
    
    /**
     * 
     * 获得指定用户指定分类下的问题列表
     * @param classId
     * @param status
     * @return
     */
    public Iterable<Question> findBySiteUserIdAndClassId(String siteUserId,String classId);
    
//    /**
//     * 
//     * 根据用户账号，姓名或者电话查询问题列表
//     * @param siteUserId
//     * @param userName
//     * @param mobile
//     * @return
//     */
//    public Iterable<Question> findBySiteUserIdOrUserNameOrMobile(String siteUserId,String userName,String mobile);
    
    
    /**
     * 通过title模糊查询用户问题列表
     * @param title
     * @return
     */
    public Iterable<Question> findByTitle(String title);
    
}
