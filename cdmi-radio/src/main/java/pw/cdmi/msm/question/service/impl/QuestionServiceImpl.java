package pw.cdmi.msm.question.service.impl;

import java.text.SimpleDateFormat;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.question.model.QuestionStatus;
import pw.cdmi.msm.question.model.entity.Question;
import pw.cdmi.msm.question.repositories.QuestionRepository;
import pw.cdmi.msm.question.service.QuestionService;

/**
 * **********************************************************
 * <br/>
 * 实现类，提供提问具体实现方法
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年5月30日
 ***********************************************************
 */

@Service
public class QuestionServiceImpl implements QuestionService {

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private QuestionRepository questionDaoImpl;

    @Override
    @Transactional
    public Question addQuestion(Question question) {
        try {
            questionDaoImpl.save(question);
            return question;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Question updateQuestion(Question question) {
        try {
            questionDaoImpl.save(question);
            return question;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Question getQuestion(String id) {
        try {
            return questionDaoImpl.findOne(id);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void closeQuestion(String id) {
        try {
            Question question = getQuestion(id);
            question.setStatus(QuestionStatus.Closed);
            questionDaoImpl.save(question);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void changeStatus(String id) {
        try {
            Question question = getQuestion(id);
            question.setStatus(QuestionStatus.Resolved);
            questionDaoImpl.save(question);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    public Iterable<Question> findQuestionBySiteUserId(String siteUserId) {
        try {
            return questionDaoImpl.findQuestionsBySiteUserId(siteUserId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<Question> findQuestionBySiteUserIdAndStatus(String siteUserId, QuestionStatus status) {
        try {
            return questionDaoImpl.findQuestionBySiteUserIdAndStatus(siteUserId, status);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<Question> findByClassId(String classId) {
        try {
            return questionDaoImpl.findByClassId(classId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<Question> findByClassIdAndStatus(String classId, QuestionStatus status) {
        try {
            return questionDaoImpl.findByClassIdAndStatus(classId, status);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<Question> findBySiteUserIdAndClassId(String siteUserId, String classId) {
        try {
            return questionDaoImpl.findBySiteUserIdAndClassId(siteUserId, classId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<Question> findByTitle(String title) {
        try {
            return questionDaoImpl.findByTitle(title);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void deleteQuestion(String id) {
        try {
            questionDaoImpl.delete(id);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
        
    }

}
