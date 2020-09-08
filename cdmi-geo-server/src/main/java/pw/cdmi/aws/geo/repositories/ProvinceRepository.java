package pw.cdmi.aws.geo.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.aws.geo.model.entities.Province;

@NoRepositoryBean
public interface ProvinceRepository extends PagingAndSortingRepository<Province, Integer>, QueryByExampleExecutor<Province>{

    /**
     * 通过国家名称获取这个国家所拥有的省份/州信息列表.<br/>
     * 
     * @param countryName
     * @return
     */
    public Iterable<Province> findByCountryId(String countryId);
    
    public Iterable<Province> findByCountryName(String countryName);
//    /**
//     * 通过国家域名获取这个国家所拥有的省份/州信息列表.<br/>
//     * 
//     * @param countryDomain
//     * @return
//     */
//    public Iterable<Province> findByCountryDomain(String countryDomain);
    
    /**
     * 通过国家编码获取这个国家所拥有的省份/州信息列表.<br/>
     * 
     * @param countryId
     * @return
     */
    public Iterable<Province> findByCountryId(Integer countryId);
    
    /**
     * 通过国家编码获取这个国家所拥有的省份/州的名称列表.<br/>
     * 
     * @param countryId
     * @return
     */
    public Iterable<Province> findNamesByCountryId(Integer countryId);
    
    /**
     * 
     * 通过省份名称获取省份/州级行政区划编号.<br/>
     * 
     * @param name 国家名称
     * @return
     */
    public Integer findIdByName(String name);
    
    /**
     * 获得指定国家所拥有的省份、州行政区划的数量
     * 
     * @param countryId
     * @return
     */
    public long countByCountryId(Integer countryId);
}
