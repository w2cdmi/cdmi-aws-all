package pw.cdmi.msm.question.service;

import pw.cdmi.msm.question.model.entity.QuestionModel;

/**
 * **********************************************************
 *
 * 接口类,提供对问题咨询模块的系列操作 
 * @author wsl
 * @version cdm Service Platform, 2016年6月30日
 ***********************************************************
 */
public interface QuestionModelService {
    /**
     * 
     * 创建一个咨询模块
     * @param questionModel
     */
    public void createQuestionModel(QuestionModel questionModel);
    
    /**
     * 
     * 判断是否重名
     * @param name
     * @return
     */
    public boolean isNameRepeat(String name);
    
    /**
     * 
     * 修改模块信息
     * @param questionModel
     */
    public void updateQuestionModel(QuestionModel questionModel);
    
    /**
     * 
     * 删除一个问题咨询模块
     * @param id
     */
    public void deleteQuestionModel(String id);
}
