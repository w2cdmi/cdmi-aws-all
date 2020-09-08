package pw.cdmi.msm.geo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.geo.model.entity.District;

public interface DistrictRepository extends PagingAndSortingRepository<District, Integer>, QueryByExampleExecutor<District>{
    
    /**
     * 通过市级行政编码获取下属的区县级行政区划单位信息列表.<br/>
     * 
     * @param cityId
     * @return
     */
    public Iterable<District> findByCityId(Integer cityId);
    
    /**
     * 通过市级行政编码获取下属的区县级行政区划单位名称列表.<br/>
     * 
     * @param cityId
     * @return
     */
    public Iterable<District> findNamesByCityId(Integer cityId);
    
    /**
     * 
     * 通过城市的名称获取区县级行政区划单位的编号.<br/>
     * 
     * @param name 国家名称
     * @return
     */
    public Integer findIdByName(String name);
    
    /**
     * 
     * 通过城市的名称和对应省级行政区划编码获取区县级行政区划单位的编码.<br/>
     * 
     * @param name 国家名称
     * @return
     */
    public Integer findIdByNameAndCityId(String name, Integer cityId);
    
    /**
     * 获得指定国家所拥有的区县级行政区划的数量
     * 
     * @param countryId
     * @return
     */
    public long countByCountryId(Integer countryId);
    
    /**
     * 获得指定省份、州所拥有的区县级行政区划的数量
     * 
     * @param provinceId
     * @return
     */
    public long countByProvinceId(Integer  provinceId);
    
    /**
     * 获得指定市级行政单位所拥有的区县级行政区划的数量
     * 
     * @param cityId
     * @return
     */
    public long countByCityId(Integer  cityId);
    
}
