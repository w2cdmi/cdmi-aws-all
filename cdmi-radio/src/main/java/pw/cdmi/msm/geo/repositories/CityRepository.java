package pw.cdmi.msm.geo.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.geo.model.entity.City;

@NoRepositoryBean
public interface CityRepository extends PagingAndSortingRepository<City, Integer>, QueryByExampleExecutor<City>{
    
    /**
     * 通过国家名称获取这个国家所拥有的市级行政区划单位信息列表.<br/>
     * 
     * @param countryName
     * @return
     */
    public Iterable<City> findByCountryName(String countryName);
    
    /**
     * 通过国家编码获取这个国家所拥有的市级行政单位信息列表.<br/>
     * 
     * @param countryId
     * @return
     */
    public Iterable<City> findByCountryId(Integer countryId);
    
    /**
     * 通过省级编码获取下属的市级行政区划单位信息列表.<br/>
     * 
     * @param provinceId
     * @return
     */
    public Iterable<City> findByProvinceId(Integer provinceId);
    
    /**
     * 通过国家编码获取这个国家所拥有的市级行政区划单位名称列表.<br/>
     * 
     * @param countryId
     * @return
     */
    public Iterable<City> findNamesByCountryId(Integer countryId);
    
    /**
     * 通过省级行政编码获取下属的市级行政区划单位名称列表.<br/>
     * 
     * @param provinceId
     * @return
     */
    public Iterable<City> findNamesByProvinceId(Integer provinceId);
    
    /**
     * 
     * 通过城市的名称获取城市级行政区划单位的编号.<br/>
     * 
     * @param name 国家名称
     * @return
     */
    public Integer findIdByName(String name);
    
    /**
     * 
     * 通过城市的名称和对应省级行政区划编码获取城市级行政区划单位的编码.<br/>
     * 
     * @param name 国家名称
     * @return
     */
    public Integer findIdByNameAndProvinceId(String name, Integer provinceId);
    
    /**
     * 获得指定国家所拥有的市级行政区划的数量
     * 
     * @param countryId
     * @return
     */
    public long countByCountryId(Integer countryId);
    
    /**
     * 获得指定省份、州所拥有的市级行政区划的数量
     * 
     * @param provinceId
     * @return
     */
    public long countByProvinceId(Integer  provinceId);
    
}
