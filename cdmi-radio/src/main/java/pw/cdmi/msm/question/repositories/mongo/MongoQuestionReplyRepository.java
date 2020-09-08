package pw.cdmi.msm.question.repositories.mongo;

import org.springframework.data.mongodb.repository.Query;

import pw.cdmi.msm.question.model.entity.QuestionReply;
import pw.cdmi.msm.question.repositories.QuestionReplyRepository;

public interface MongoQuestionReplyRepository extends QuestionReplyRepository {
    @Override
    @Query("{'questionId' : ?0}")
    public Iterable<QuestionReply> findReplyByQuestionId(String questionId);
    
    @Override
    @Query("{'questionId' : ?0 , 'isAccepted' : ?1}")
    public QuestionReply findByQuestionIdAndIsAccepted(String questionId,boolean isAccepted);
}
