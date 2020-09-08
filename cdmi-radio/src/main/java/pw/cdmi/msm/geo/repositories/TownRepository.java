package pw.cdmi.msm.geo.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import pw.cdmi.msm.geo.model.entity.Town;

@NoRepositoryBean
public interface TownRepository extends PagingAndSortingRepository<Town, Long>, QueryByExampleExecutor<Town>{

    /**
     * 通过区县级行政编码获取下属的乡镇级行政区划单位信息列表.<br/>
     * 
     * @param districtId
     * @return
     */
    public Iterable<Town> findByDistrictId(Integer districtId);
    
    /**
     * 通过区县级行政区划编码获取乡镇级行政区划单位名称列表.<br/>
     * 
     * @param districtId
     * @return
     */
    public Iterable<Town> findNamesByDistrictId(Integer districtId);
    
    /**
     * 
     * 通过乡镇、街道办名称和对应区县行政区划编码获取乡镇级行政区划单位的编码.<br/>
     * 
     * @param name 乡镇名称
     * @param districtId 区县行政区划编号
     * @return
     */
    public long findIdByNameAndDistrictId(String name, int districtId);
    
    /**
     * 获得指定国家所拥有的乡镇级行政区划的数量
     * 
     * @param countryId
     * @return
     */
    public long countByCountryId(Integer countryId);
    
    /**
     * 获得指定省份、州所拥有的乡镇级行政区划的数量
     * 
     * @param provinceId
     * @return
     */
    public long countByProvinceId(Integer provinceId);
    
    /**
     * 获得指定市级行政单位所拥有的乡镇级行政区划的数量
     * 
     * @param cityId
     * @return
     */
    public long countByCityId(Integer cityId);
    
    /**
     * 获得指定区县级行政区划单位所拥有的乡镇级行政区划的数量
     * 
     * @param cityId
     * @return
     */
    public long countByDistrictId(Integer districtId);
}
