package pw.cdmi.msm.question.repositories.mongo;


import org.springframework.data.mongodb.repository.Query;

import pw.cdmi.core.db.MongoRepositoryBean;
import pw.cdmi.msm.question.model.QuestionStatus;
import pw.cdmi.msm.question.model.entities.Question;
import pw.cdmi.msm.question.repositories.QuestionRepository;

@MongoRepositoryBean
public interface MongoQuestionRepository extends QuestionRepository{
    @Override
    @Query(value="{'siteUserId' : ?0}")
    public Iterable<Question> findQuestionsBySiteUserId(String id);
    
    @Override
    @Query(value="{'siteUserId' : ?0,'status' : ?1}")
    public Iterable<Question> findQuestionBySiteUserIdAndStatus(String siteUSerId,QuestionStatus status);
    
    @Override
    @Query("{'classId' : ?0 , 'status' :?1}")
    public Iterable<Question> findByClassIdAndStatus(String classId,QuestionStatus status);
    
    @Override
    @Query("{'siteUserId' : ?0 ,'classId' : ?1}")
    public Iterable<Question> findBySiteUserIdAndClassId(String siteUserId,String classId);
    
//    @Override
//    @Query("{'siteUserId'}")
//    public Iterable<Question> findBySiteUserIdOrUserNameOrMobile(String siteUserId,String userName,String mobile);
    
    @Override
    @Query("{'title':{$regex:'?0'}}")
    public Iterable<Question> findByTitle(String title);
    
}
