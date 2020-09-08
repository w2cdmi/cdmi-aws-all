package pw.cdmi.msm.question.repositories.jpa;

import pw.cdmi.msm.question.repositories.QuestionModelRepsotory;

public interface JpaQuestionModelRepsitory extends QuestionModelRepsotory{
    @Override
    public long countByName(String name);
}
