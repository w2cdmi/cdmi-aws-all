package pw.cdmi.msm.question.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.question.model.entity.QuestionModel;
@NoRepositoryBean
public interface QuestionModelRepsotory extends PagingAndSortingRepository<QuestionModel, String>,QueryByExampleExecutor<QuestionModel>{
    /**
     * 
     * 根据名字查询模块是否存在
     * @param name
     * @return
     */
    public long countByName(String name);
}
