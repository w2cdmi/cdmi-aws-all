package pw.cdmi.aws.geo.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.aws.geo.model.entities.Country;

@NoRepositoryBean
public interface CountryRepository extends PagingAndSortingRepository<Country, Integer>, QueryByExampleExecutor<Country>{

    /**
     * 
     * 通过国家名称获取国家的详细信息.<br/>
     * 
     * @param name 国家名称
     * @return
     */
    public Integer findIdByName(String name);
    
    /**
     * 
     * 通过国家域名获取国家的详细信息.<br/>
     * 
     * @param name 国家名称
     * @return
     */
    public Integer findIdByDomain(String domain);
    
    /**
     * 获得所有的国家名称列表.<br>
     * 
     * @return
     */
    public Iterable<Country> findNames();
    
    /**
     * 获得所有的主权国家名称列表.<br>
     * 
     * @return
     */
    public Iterable<Country> findNamesBySovereign();
    
    /**
     * 获得所有的国家列表.<br>
     * 
     * @return
     */
    public Iterable<Country> findBySovereign();
}
