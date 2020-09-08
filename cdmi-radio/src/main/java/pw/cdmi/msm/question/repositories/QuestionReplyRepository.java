package pw.cdmi.msm.question.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.question.model.entity.QuestionReply;
@NoRepositoryBean
public interface QuestionReplyRepository extends PagingAndSortingRepository<QuestionReply, String>,QueryByExampleExecutor<QuestionReply>{
    /**
     * 
     * 根据问题id查询回复列表
     * @param questionId
     * @return
     */
    public Iterable<QuestionReply> findReplyByQuestionId(String questionId);
    
    /**
     * 查询指定问题的采纳回复
     * @param questionId
     * @param isAccepted
     * @return
     */
    public QuestionReply findByQuestionIdAndIsAccepted(String questionId,boolean isAccepted);
}
