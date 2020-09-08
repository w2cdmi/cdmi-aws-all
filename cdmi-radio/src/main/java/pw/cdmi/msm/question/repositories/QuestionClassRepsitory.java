package pw.cdmi.msm.question.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.question.model.entity.QuestionClass;
/**
 * **********************************************************
 * 接口类,提供对问题类型的系列操作
 * @author wsl
 * @version cdm Service Platform, 2016年6月30日
 ***********************************************************
 */
@NoRepositoryBean
public interface QuestionClassRepsitory extends PagingAndSortingRepository<QuestionClass, String>,QueryByExampleExecutor<QuestionClass>{
    /**
     * 根据名字查询问题类型存在数量
     * @param name
     * @return
     */
    public long countByName(String name);
}
