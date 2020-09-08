package pw.cdmi.msm.question.repositories.jpa;


import pw.cdmi.msm.question.repositories.QuestionClassRepsitory;

public interface JpaQuestionClassRepsitory extends QuestionClassRepsitory{
    @Override
    public long countByName(String name);
}
