package pw.cdmi.msm.question.repositories.jpa;


import org.springframework.data.jpa.repository.Query;

import pw.cdmi.msm.question.model.entity.QuestionReply;
import pw.cdmi.msm.question.repositories.QuestionReplyRepository;

public interface JpaQuestionReplyRepository extends QuestionReplyRepository {
    @Override
    @Query("FROM QuestionReply r where r.questionID=:questionId")
    public Iterable<QuestionReply> findReplyByQuestionId(String questionId);
}