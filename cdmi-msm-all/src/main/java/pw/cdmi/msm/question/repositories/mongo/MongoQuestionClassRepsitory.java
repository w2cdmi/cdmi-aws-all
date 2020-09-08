package pw.cdmi.msm.question.repositories.mongo;

import org.springframework.data.mongodb.repository.Query;

import pw.cdmi.core.db.MongoRepositoryBean;
import pw.cdmi.msm.question.repositories.QuestionClassRepsitory;

@MongoRepositoryBean
public interface MongoQuestionClassRepsitory extends QuestionClassRepsitory {
    @Override
    @Query(count=true ,value="{'name' : ?0}")
    public long countByName(String name);
}
