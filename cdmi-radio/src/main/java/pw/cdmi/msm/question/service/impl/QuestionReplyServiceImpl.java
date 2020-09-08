package pw.cdmi.msm.question.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.question.model.entity.QuestionReply;
import pw.cdmi.msm.question.repositories.QuestionReplyRepository;
import pw.cdmi.msm.question.service.QuestionReplyService;

/**
 * **********************************************************
 * 实现类 实现对问题回复的具体处理
 * @author wsl
 * @version cdm Service Platform, 2016年6月30日
 ***********************************************************
 */
@Service
public class QuestionReplyServiceImpl implements QuestionReplyService {

    @Autowired
    private QuestionReplyRepository questionReplyDaoImpl;

    @Override
    public QuestionReply createQuestionReply(QuestionReply questionReply) {
        try {
            questionReplyDaoImpl.save(questionReply);
            return questionReply;
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public void deleteQuestionReply(String id) {
        try {
            questionReplyDaoImpl.delete(id);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }

    }

    @Override
    public void updateQuestionReply(QuestionReply questionReply) {
        try {
            questionReplyDaoImpl.save(questionReply);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public QuestionReply getQuestionReply(String id) {
        try {
            return questionReplyDaoImpl.findOne(id);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

    @Override
    public Iterable<QuestionReply> findByQuestionId(String questionId) {
        try {
            return questionReplyDaoImpl.findReplyByQuestionId(questionId);
        } catch (Exception e) {
            throw new AWSServiceException(SystemReason.SQLError);
        }
    }

}
