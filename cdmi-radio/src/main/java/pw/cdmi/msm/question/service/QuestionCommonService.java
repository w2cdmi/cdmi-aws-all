package pw.cdmi.msm.question.service;

import pw.cdmi.msm.question.model.entity.QuestionCommon;

/**
 * **********************************************************
 * 接口类，对常见问题的系列操作
 * @author wsl
 * @version cdm Service Platform, 2016年6月30日
 ***********************************************************
 */
public interface QuestionCommonService {
    /**
     * 向网站添加一条常见问题
     * @param questionCommon
     * @return
     */
    public QuestionCommon addQuestionCommon(QuestionCommon questionCommon);
    
    /**
     * 
     * 修改指定常见问题
     * 
     * @param questionId
     * @return
     */
    public QuestionCommon updateQuestionCommon(QuestionCommon questionCommon);
    
    /**
     * 删除一条常见问题
     * @param id
     */
    public void deleteQuestionCommon(String id);
    
    /**
     * 
     * 根据指定分类id查询常见问题列表
     * @param classId
     * @return
     */
    public Iterable<QuestionCommon> findByClassId(String classId);

    /**
     * 
     * 查询所有常见问题
     * @return
     */
    public Iterable<QuestionCommon> findAllCommon();
    
    
   /**
    * 
    * 根据传入标题查询常见问题是否存在
    * @param title
    * @return
    */
    public boolean isTitleRepeat(String title);
    
    /**
     * 
     * 根据id查询常见问题信息
     * @param id
     * @return
     */
    public QuestionCommon getById(String id);
    
    /**
     * 
     * 根据orderNumber查询常见问题
     * @param orderNumber
     * @return
     */
    public QuestionCommon getByOrderNumer(Integer orderNumber);
    
    /**
     * 
     * 根据orderNumber查询常见问题列表
     * @param orderNumber
     * @return
     */
    public Iterable<QuestionCommon> findByOrderNumber(Integer orderNumber);
}
