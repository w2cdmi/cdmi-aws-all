package pw.cdmi.msm.question.service;

import pw.cdmi.msm.question.model.entities.QuestionClass;

/**
 * **********************************************************
 * 接口类,对问题类型的系列操作
 * @author wsl
 * @version cdm Service Platform, 2016年6月30日
 ***********************************************************
 */
public interface QuestionClassService {
    /**
     * 判断分类名称是否存在
     * @param name
     * @return
     */
    public boolean isNameRepeat(String name);
    
    /**
     * 
     * 新建一条新的问题分类
     * @param questionClass
     */
    public void addQuestionClass(QuestionClass questionClass);
    
    /**
     * 
     * 删除一条分类信息
     * @param id
     */
    public void deleteQuestionClass(String id);
    
    /**
     * 编辑指定的问题分类信息
     * @param id
     */
    public void updateQuestionClass(QuestionClass questionClass);
    
    /**
     * 
     * 根据id查询问题类型信息
     * @param id
     * @return
     */
    public QuestionClass getQuestionClass(String id);
}
