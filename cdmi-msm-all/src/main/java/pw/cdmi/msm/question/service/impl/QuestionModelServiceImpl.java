package pw.cdmi.msm.question.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.question.model.entities.QuestionModel;
import pw.cdmi.msm.question.repositories.QuestionModelRepsotory;
import pw.cdmi.msm.question.service.QuestionModelService;

/**
 * **********************************************************
 * 对咨询问题模块的具体实现
 * @author wsl
 * @version cdm Service Platform, 2016年6月30日
 ***********************************************************
 */
@Service
public class QuestionModelServiceImpl implements QuestionModelService {

    @Autowired
    private QuestionModelRepsotory questionModelDaoImpl;

    @Override
    public void createQuestionModel(QuestionModel questionModel) {
        try {
            questionModelDaoImpl.save(questionModel);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public boolean isNameRepeat(String name) {
        try {
            long count = questionModelDaoImpl.countByName(name);
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void updateQuestionModel(QuestionModel questionModel) {
        try {
            questionModelDaoImpl.save(questionModel);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
        
    }

    @Override
    public void deleteQuestionModel(String id) {
        try {
            questionModelDaoImpl.delete(id);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
        
    }

}
