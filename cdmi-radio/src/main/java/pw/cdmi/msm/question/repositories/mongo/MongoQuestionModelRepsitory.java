package pw.cdmi.msm.question.repositories.mongo;

import org.springframework.data.mongodb.repository.Query;

import pw.cdmi.msm.question.repositories.QuestionModelRepsotory;

public interface MongoQuestionModelRepsitory extends QuestionModelRepsotory {

    @Override
    @Query(count=true,value="{'name' : ?0}")
    public long countByName(String name);
}
