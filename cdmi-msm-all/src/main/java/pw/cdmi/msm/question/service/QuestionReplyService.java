package pw.cdmi.msm.question.service;

import pw.cdmi.msm.question.model.entities.QuestionReply;

/**
 * **********************************************************
 * 接口类,对问题的回复操作接口
 * @author wsl
 * @version cdm Service Platform, 2016年6月28日
 ***********************************************************
 */
public interface QuestionReplyService {
    /**
     * 
     * 为问题创建一条回复
     * @param questionReply
     * @return
     */
    public QuestionReply createQuestionReply(QuestionReply questionReply);
    
    /**
     * 
     * 修改问题回复
     * @param questionReply
     */
    public void updateQuestionReply(QuestionReply questionReply);
    
    /**
     * 
     * 删除一条问题回复
     * @param id
     */
    public void deleteQuestionReply(String id);
    
    /**
     * @param id
     * @return
     */
    public QuestionReply getQuestionReply(String id);
    
    /**
     * 
     * 根据问题id查询回复列表
     * @param questionId
     * @return
     */
    public Iterable<QuestionReply> findByQuestionId(String questionId);
    
}
