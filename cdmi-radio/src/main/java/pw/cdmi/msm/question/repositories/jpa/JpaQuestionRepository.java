package pw.cdmi.msm.question.repositories.jpa;

import org.springframework.data.jpa.repository.Query;

import pw.cdmi.msm.question.model.QuestionStatus;
import pw.cdmi.msm.question.model.entity.Question;
import pw.cdmi.msm.question.repositories.QuestionRepository;

public interface JpaQuestionRepository extends QuestionRepository{
    @Override
    @Query("FROM Question q WHERE q.siteUserId = :siteUserId")
    public Iterable<Question> findQuestionsBySiteUserId(String id);
    
    @Override
    @Query("FROM Question q WHERE q.siteUserId = :siteUserId AND q.status = :status")
    public Iterable<Question> findQuestionBySiteUserIdAndStatus(String siteUserId,QuestionStatus status);
    
    @Override
    @Query("FROM Question q WHERE q.siteUserId = :siteUserId AND q.status = :status")
    public Iterable<Question> findByClassIdAndStatus(String classId,QuestionStatus status);
    
    public Iterable<Question> findBySiteUserIdAndClassId(String siteUserId,String classId);
}
