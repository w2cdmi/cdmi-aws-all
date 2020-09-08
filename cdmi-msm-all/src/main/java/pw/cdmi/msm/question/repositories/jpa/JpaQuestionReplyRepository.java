package pw.cdmi.msm.question.repositories.jpa;


import org.springframework.data.jpa.repository.Query;

import pw.cdmi.msm.question.model.entities.QuestionReply;
import pw.cdmi.msm.question.repositories.QuestionReplyRepository;

public interface JpaQuestionReplyRepository extends QuestionReplyRepository {
    @Override
    @Query("FROM QuestionReply r where r.questionId=:questionId")
    public Iterable<QuestionReply> findReplyByQuestionId(String questionId);
}