package pw.cdmi.msm.question.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.question.model.entities.QuestionCommon;
import pw.cdmi.msm.question.repositories.QuestionCommonRepository;
import pw.cdmi.msm.question.service.QuestionCommonService;

/**
 * **********************************************************
 * 实现类 , 对常见问题的一系列操作实现
 * @author wsl
 * @version cdm Service Platform, 2016年6月29日
 ***********************************************************
 */
@Service
public class QuestionCommonServiceImpl implements QuestionCommonService {

    @Autowired
    private QuestionCommonRepository questionCommonDaoImpl;

    @Override
    public QuestionCommon addQuestionCommon(QuestionCommon questionCommon) {
        try {
            questionCommonDaoImpl.save(questionCommon);
            return questionCommon;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public QuestionCommon updateQuestionCommon(QuestionCommon questionCommon) {
        try {
            questionCommonDaoImpl.save(questionCommon);
            return questionCommon;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void deleteQuestionCommon(String id) {
        try {
            questionCommonDaoImpl.delete(id);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<QuestionCommon> findByClassId(String classId) {
        try {
            return questionCommonDaoImpl.findByClassId(classId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public boolean isTitleRepeat(String title) {
        try {
            long count = questionCommonDaoImpl.countByTitle(title);
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
    public QuestionCommon getById(String id) {
        try {
            return questionCommonDaoImpl.findOne(id);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public QuestionCommon getByOrderNumer(Integer orderNumber) {
        try {
            return questionCommonDaoImpl.getByOrderNumber(orderNumber);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<QuestionCommon> findByOrderNumber(Integer orderNumber) {
        try {
            return questionCommonDaoImpl.findByOrderNumber(orderNumber);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<QuestionCommon> findAllCommon() {
        try {
            return questionCommonDaoImpl.findAll();
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

}
