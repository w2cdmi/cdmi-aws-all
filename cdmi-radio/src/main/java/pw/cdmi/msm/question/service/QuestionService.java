package pw.cdmi.msm.question.service;


import pw.cdmi.msm.question.model.QuestionStatus;
import pw.cdmi.msm.question.model.entity.Question;

/**
 * **********************************************************
 * <br/>
 * 接口类，用户提交问题的操作接口
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年5月27日
 ***********************************************************
 */
public interface QuestionService {

    /**
     * 创建用户问题
     * @param Question
     * @return  
     */
   public Question addQuestion(Question question);
   
   /**
    * 
    * 得到一条问题的详细信息
    * @param id
    * @return
    */
   public Question getQuestion(String id);

   /**
    * 
    * 对自己提出的问题进行修改补充
    * 
    * @param questionId
    * @return
    */
   public Question updateQuestion(Question question);
   
   /**
    * 
    * 删除一条问题
    * @param id
    */
   public void deleteQuestion(String id);
   
   
   /**
    * 
    * 关闭提出的问题
    * 
    * @param id
    */
   public void closeQuestion(String id);
   
   /**
    * 
    * 用户标记自己的问题已经得到解决
    * @param id
    */
   public void changeStatus(String id);
   
   /**
    * 
    * 查找用户提出的问题列表
    * @return
    */
   public Iterable<Question> findQuestionBySiteUserId(String siteUserId);
   
   /**
    * 
    * 根据用户id和问题状态查询问题列表
    * @param suteUserId
    * @param status
    * @return
    */
   public Iterable<Question> findQuestionBySiteUserIdAndStatus(String siteUserId,QuestionStatus status);
   
   /**
    * 
    * 根据问题的类型查询问题列表
    * @param classId
    * @return
    */
   public Iterable<Question> findByClassId(String classId);
   
   /**
    * 
    * 根据问题类型和问题状态查询问题列表
    * @param classId
    * @param status
    * @return
    */
   public Iterable<Question> findByClassIdAndStatus(String classId,QuestionStatus status);
   
   /**
    * 
    * 查询指定用户在指定分类下的问题列表
    * @param siteUserId
    * @param classId
    * @return
    */
   public Iterable<Question> findBySiteUserIdAndClassId(String siteUserId,String classId);
   
   /**
    * 
    * 根据问题标题模糊查询问题列表
    * @param title
    * @return
    */
   public Iterable<Question> findByTitle(String title);
   
  
}
