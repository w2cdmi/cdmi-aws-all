package pw.cdmi.msm.question.repositories.mongo;

import org.springframework.data.mongodb.repository.Query;

import pw.cdmi.core.db.MongoRepositoryBean;
import pw.cdmi.msm.question.model.entities.QuestionCommon;
import pw.cdmi.msm.question.repositories.QuestionCommonRepository;

@MongoRepositoryBean
public interface MongoQuestionCommonRepository extends QuestionCommonRepository{
    @Override
    @Query(value="{'classId' : ?0}")
    public Iterable<QuestionCommon> findByClassId(String classId);
    
    @Override
    @Query(count=true ,value="{'title' : ?0}")
    public long countByTitle(String title);
    
    @Override
    @Query("{'oederNumber': {$lt: ?0}}")
    public Iterable<QuestionCommon> findByOrderNumber(Integer orderNumber);
    
    @Override
    @Query("{'oederNumber' : ?0}")
    public QuestionCommon getByOrderNumber(Integer orderNumber);
    
}
