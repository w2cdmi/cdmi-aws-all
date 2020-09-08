package pw.cdmi.msm.question.repositories.jpa;


import org.springframework.data.jpa.repository.Query;

import pw.cdmi.msm.question.model.entity.QuestionCommon;
import pw.cdmi.msm.question.repositories.QuestionCommonRepository;

public interface JpaQuestionCommonRepository extends QuestionCommonRepository {
    @Override
    @Query("FROM QuestionCommon c where c.classId=:classId")
    public Iterable<QuestionCommon> findByClassId(String classId);

    @Override
    public long countByTitle(String title);
}
