package pw.cdmi.msm.question.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.question.model.entity.QuestionClass;
import pw.cdmi.msm.question.repositories.QuestionClassRepsitory;
import pw.cdmi.msm.question.service.QuestionClassService;

/**
 * **********************************************************
 * 实现类，对常见问题业务操作的具体实现
 * @author wsl
 * @version cdm Service Platform, 2016年6月30日
 ***********************************************************
 */
@Service
public class QuestionClassServiceImpl implements QuestionClassService {

    @Autowired
    private QuestionClassRepsitory questionClassDaoImpl;

    @Override
    public boolean isNameRepeat(String name) {
        try {
            long count = questionClassDaoImpl.countByName(name);
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
    public void addQuestionClass(QuestionClass questionClass) {
        try {
            questionClassDaoImpl.save(questionClass);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void deleteQuestionClass(String id) {
        try {
            questionClassDaoImpl.delete(id);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
        
    }

    @Override
    public void updateQuestionClass(QuestionClass questionClass) {
        try {
            questionClassDaoImpl.save(questionClass);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public QuestionClass getQuestionClass(String id) {
       try {
        return questionClassDaoImpl.findOne(id);
    } catch (Exception e) {
        throw new AWSServiceException(SystemReason.SQLError);
    }
       
    }

}
